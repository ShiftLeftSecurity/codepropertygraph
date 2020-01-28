package io.shiftleft.semanticcpg.irdumper
import io.shiftleft.codepropertygraph.generated.{Languages, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{HasArgumentIndex, StoredNode}
import io.shiftleft.overflowdb.OdbNode
import io.shiftleft.semanticcpg.codedumper.CodeDumper
import org.apache.logging.log4j.LogManager
import io.shiftleft.semanticcpg.utils._
import io.shiftleft.codepropertygraph.generated.Operators
import scala.collection.mutable.Set
import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters._
import scala.collection.Searching._

object Tag extends Enumeration{
  type Tag = Value
  val SSA, RET, CALL_STATIC, ASSIGN, BINARY, THIS_CALL, PTR_CALL, UNARY, UNKNOWN, UNKOWN_CC, ID, LOC_REF, LIT= Value
}

class treeEntry(val tag:Tag.Value,
                val depth:Int,
                val idx:Int,
                var args:List[Int],
                var receiver: Option[Int],
                var printLoad: String*,
               ){
}



object IRDumper {
  private val logger = LogManager.getLogger(IRDumper)
  //val t = new treeEntry(Tag.SSA, 1, 2, List(3), None, "foo", "bar", "z")

  def DFO(entry: nodes.CfgNode, visited: scala.collection.mutable.Set[StoredNode] = scala.collection.mutable.Set[StoredNode]() ): scala.collection.mutable.ArrayBuffer[StoredNode] = {
    val worklist = scala.collection.mutable.Stack[StoredNode]()
    val res = scala.collection.mutable.ArrayBuffer[StoredNode]()

    worklist.push(entry)
    visited.add(entry)
    while(!worklist.isEmpty){
      val node = worklist.pop()
      visited.add(node)
      val nbo = node._cfgOut.asScala.toList.filter{n => !visited.contains(n)}
      if(nbo.isEmpty){
        res.addOne(node)
      }
      else {
        worklist.push(node)
      }
    }
   return res.reverse
  }

  def multiDFO(meth: nodes.Method): Array[StoredNode] ={
    val res = scala.collection.mutable.ArrayBuffer[StoredNode]()
    val visited = scala.collection.mutable.Set[StoredNode]()
    res.appendAll(DFO(meth, visited))
    for(n <- meth._containsOut().asScala){
      if(!visited.contains(n) && n.isInstanceOf[nodes.CfgNode]){res.appendAll(DFO(meth, visited))}
    }
    return res.toArray
  }
  def revMap(nodeArray: Array[StoredNode]): scala.collection.mutable.Map[StoredNode, Int] ={
    val invMap = scala.collection.mutable.Map[StoredNode, Int]()
    for((n, idx) <- nodeArray.view.zipWithIndex){
      invMap(n) = idx
    }
    return invMap
  }

  def BBsplits(nodeArray: Array[nodes.StoredNode], nodeMap: scala.collection.mutable.Map[StoredNode, Int]): Array[Int] = {
    val edges = scala.collection.mutable.ArrayBuffer[Long]()
    val BBentries = scala.collection.mutable.Set[Int]()
    //val BBexits = scala.collection.mutable.Set[Int]()
    for((node, idx) <- nodeMap){
      val entries = node._cfgIn.asScala.toList
      if(entries.size != 1){
        BBentries.add(idx)
        entries.foreach{n => BBentries.add(nodeMap(n)-1)}
      }
      val exits =  node._cfgOut.asScala.toList
      if(exits.size != 1){
        nodes.Local
        BBentries.add(idx-1)
        exits.foreach{n => BBentries.add(nodeMap(n))}
      }
    }
    BBentries.remove(-1)

   BBentries.toArray.sorted
  }

  def BBfind(BB: Array[Int], idx: Int): Tuple3[Int,Int,Int] = {
    val pos = BB.search(idx) match {
      case InsertionPoint(i) => i-1
      case Found(i) => i
    }
    val bbF = BB(pos)
    val bbL = if (pos < BB.length) BB(pos+1)-1 else BB.length-1
    return (pos, bbF, bbL)
  }

  def genEdges(BB:Array[Int], nodeMap:scala.collection.mutable.Map[StoredNode, Int], nodes:Array[StoredNode]):Tuple2[Array[List[Int]], Array[List[Int]]] = {
    val preds =Array.fill(BB.size){null:List[Int]}
    val succs =Array.fill(BB.size){Nil:List[Int]}
    for((f, i) <- BB.view.zipWithIndex){
      preds(i) = nodes(f)._cfgIn.asScala.map{pred => BBfind(BB, nodeMap(pred))._1}.toList.sorted
      succs(i) = nodes(if(i<BB.length) BB(i+1)-1 else BB.length -1)._cfgOut.asScala.map{succ => BBfind(BB, nodeMap(succ))._1}.toList.sorted
    }
    (preds, succs)
  }

  def inlineInsts(BB: Array[Int], nodesArray:Array[StoredNode]):Unit = {
    val stack = scala.collection.mutable.Stack[StoredNode]()
    val inlineMap = Array.fill(nodesArray.length){false}

    for(i <- Range(0, BB.length)) {
      val ran = Range(BB(i), if(i+1< BB.length) BB(i+1) else nodesArray.length)
      for (j <- ran.reverse) {
        val node = nodesArray(j)
        val evict = stack.popWhile {
          _ != node
        }
        if (!stack.isEmpty && stack.pop() == node && node._argumentIn().asScala.length < 2) {
          inlineMap(j) = true
        }
        node match {
          case (_: nodes.Call) | (_: nodes.Return) => {
            val toAdd = node._argumentOut().asScala.toList.asInstanceOf[List[StoredNode with HasArgumentIndex]].sortBy(_.argumentIndex).reverse
            stack.addAll(node._argumentOut().asScala.toList.asInstanceOf[List[StoredNode with HasArgumentIndex]].sortBy(_.argumentIndex).reverse)
            if(node._receiverOut.asScala.length > 0){
              stack.addAll(node._receiverOut.asScala.toList.filter(n => !toAdd.contains(n)))
            }
          }
          case _ => {}
        }
      }
    }
    return inlineMap
  }
  def OpMap(node: StoredNode, locals: List[StoredNode]):Tuple3[Tag.Value, String, String] = {
    node match {
      case call: nodes.Call => {
        val opMatch = call.methodFullName match {
          case Operators.addition => (Tag.BINARY, "+", "")
          case Operators.subtraction => (Tag.BINARY, "-", "")
          case Operators.multiplication => (Tag.BINARY, "*", "")
          case Operators.division => (Tag.BINARY, "/", "")
          case Operators.exponentiation => (Tag.BINARY, "**", "")
          case Operators.modulo => (Tag.BINARY, "%", "")
          case Operators.shiftLeft => (Tag.BINARY, "<<", "")
          case Operators.logicalShiftRight => (Tag.BINARY, ">>", "")
          case Operators.arithmeticShiftRight => (Tag.BINARY, ">>>", "")
          case Operators.not => (Tag.UNARY, "~", "")
          case Operators.and => (Tag.BINARY, "&", "")
          case Operators.or => (Tag.BINARY, "|", "")
          case Operators.xor => (Tag.BINARY, "^", "")
          case Operators.minus => (Tag.UNARY, "-", "")
          case Operators.plus => (Tag.UNARY, "+", "")
          case Operators.preIncrement => (Tag.UNARY, "++", "")
          case Operators.preDecrement => (Tag.UNARY, "--", "")
          case Operators.postIncrement => (Tag.UNARY, "", "++")
          case Operators.postDecrement => (Tag.UNARY, "", "--")
          case Operators.logicalNot => (Tag.UNARY, "!", "")
          case Operators.logicalOr => (Tag.BINARY, "||", "")
          case Operators.logicalAnd => (Tag.BINARY, "&&", "")

          case Operators.equals => (Tag.BINARY, "==", "")
          case Operators.notEquals => (Tag.BINARY, "!=", "")
          case Operators.greaterThan => (Tag.BINARY, ">", "")
          case Operators.lessThan => (Tag.BINARY, "<", "")
          case Operators.greaterEqualsThan => (Tag.BINARY, ">=", "")
          case Operators.lessEqualsThan => (Tag.BINARY, "<=", "")

          case Operators.indirection => (Tag.UNARY, "", "*")
          case Operators.addressOf => (Tag.UNARY, "", "&")

          case Operators.fieldAccess => (Tag.BINARY, ".", "")
          case Operators.memberAccess => (Tag.BINARY, ".", "")
          case Operators.indirectMemberAccess => (Tag.BINARY, ".", "")
          case Operators.indirectFieldAccess => (Tag.BINARY, "->", "")
          case Operators.indexAccess => (Tag.BINARY, "[", "]")
          case Operators.computedMemberAccess => (Tag.BINARY, "[", "]")
          case Operators.indirectComputedMemberAccess => (Tag.BINARY, "[", "]")
          case Operators.getElementPtr => (Tag.BINARY, "GEP[", "]")
          case Operators.indirectIndexAccess => (Tag.BINARY, "C[", "]")

          case Operators.assignment => (Tag.ASSIGN, "=", "")
          case _ => (Tag.UNKNOWN, "", "")
        }
        if (opMatch._1 != Tag.UNKNOWN) return opMatch
        if (call._receiverOut.hasNext()) {
          val rec = call._receiverOut().next()
          if (rec.isInstanceOf[nodes.HasArgumentIndex] && rec.asInstanceOf[HasArgumentIndex].argumentIndex == 0 && rec._argumentIn.asScala.nextOption() == Some(call))
            return (Tag.THIS_CALL, "", "")
          else if (!rec._argumentIn().hasNext()) {
            return (Tag.PTR_CALL, "", "")
          }
          else return (Tag.UNKOWN_CC, "", "")
        }
        if (call.dispatchType == "STATIC_DISPATCH")
          return (Tag.CALL_STATIC, "", "")
        else return (Tag.UNKOWN_CC, "", "")
      }
      case _:nodes.Return => (Tag.RET, "", "")
      case n:nodes.FieldIdentifier => (Tag.ID, "", "")
      case ell:nodes.Literal => (Tag.LIT, "", "")

    }


        throw new NotImplementedError()
  }
  def mktreeEntry(idx:Int, depth:Int, nodesArray: Array[StoredNode], inlineMap: Array[Boolean], nodeMap: scala.collection.mutable.Map[StoredNode, Int]):Unit = {
    val node = nodesArray(idx)
    if(depth > 0 && !inlineMap(idx)){
      return new treeEntry(Tag.SSA, depth, idx, Nil, None)
    }
    else {

    }
    return new treeEntry(idx, depth, nodes)
    }
    io.shiftleft.codepropertygraph.generated.Operators.not

    val openLine = node match {
      case (n:nodes.Call) => {
        n.dispatchType == "STATIC_DISPATCH" || MemberAccess.isGenericMemberAccessName(n.methodFullName)
      }
      case (_:nodes.Return) => true
      case _ => false
    }
  }
  def mktrees(ran: Range, nodesArray: Array[StoredNode], inlineMap: Array[Boolean], nodeMap: scala.collection.mutable.Map[StoredNode, Int]):Unit = {
    val trees = ArrayBuffer[treeEntry]()

//class treeEntry(val tag:Tag, val depth:Int, newLine:Boolean, val idx:Int, var args:List[Int], var rec: Option[Int]){

  }




  class dumperBuf(val BB: Array[Int], val fwEdges:Array[Long], val bwEdges: Array[Long], val nodes: Array[nodes.StoredNode], val nodeMap:Map[StoredNode, Int]) {

  }


  def dump(meth: nodes.Method):String = {

    val worklist = scala.collection.mutable.Stack[StoredNode]()
    val visited = scala.collection.mutable.Set[StoredNode]()
    //val bbLasts = scala.collection.mutable.Set[Int]()
    val bbFirsts = scala.collection.mutable.Set[Int]()
    var num = 0
    val res = ArrayBuffer[StoredNode]()
    val invRes =  scala.collection.mutable.Map[StoredNode, Int]()
    worklist.addAll(meth._containsIn.asScala)
    worklist.addOne(meth)
    while(!worklist.isEmpty){
      val item = worklist.pop()
      if(!visited.contains(item) && !item.isInstanceOf[nodes.MethodReturn]){
        val nx = item._cfgOut.asScala.toArray.sortBy{_.asInstanceOf[nodes.CfgNode].order}
        res.append(item)
        invRes.addOne((item, num))
        num += 1
        worklist.addAll(nx)
      }
    }
    var i = 0
    while(i < res.length){
      val node = res(i)
      val nx = node._cfgOut.asScala.toSeq
      if(nx.length != 1){
       // bbLasts.add(i)
        bbFirsts.addAll(nx.map{invRes.getOrElse(_, -1)})
      }
      val prevs = node._cfgIn.asScala.toSeq
      if(prevs.length != 1){
        bbFirsts.add(i)
       // bbLasts.addAll(nx.map{invRes.getOrElse(_, -1)})
      }
      i += 1
    }
    bbFirsts.remove(-1)
    val bbF = bbFirsts.toArray.sorted
    val preds = Array.fill(bbF.length){Nil.asInstanceOf[List[Int]]}
    val succs = Array.fill(bbF.length){Nil.asInstanceOf[List[Int]]}
    i = 0
    while (i < bbF.length){
      val tmp = res(bbF(i))._cfgIn.asScala.map{n => findBBnum(bbF, invRes.getOrElse(n, -1))}.toList
      preds(i) = tmp
      tmp.map{s => succs(s) = i::succs(i)}
      i+=1
    }
    val inlineMap = Array.fill(bbF.length){false}

    i = 0
    while (i < bbF.length){
      val bbFirst = bbF(i)
      val bbLast = if(i < bbF.length+1 ) {bbF(i+1)} else {res.length}
      var j = bbLast
      var stack = Nil.asInstanceOf[List[StoredNode]]
      while(j > bbFirst){
        j -= 1

        //try to match stack
        while(!stack.isEmpty && stack.head != res(j)){
          stack = stack.drop(1)
        }
        if(!stack.isEmpty && stack.head == res(j)){
          inlineMap(j) = true
        }
        //build up more stack
        val newStack = res(j)._argumentOut.asScala.map(_.asInstanceOf[StoredNode with HasArgumentIndex])
                .toSeq.sortBy(n=> -n.argumentIndex)
        stack = stack.prependedAll(newStack)
      }
    }


    //loopdepth
    val loopDepth = Array.fill(bbF.length){0}


   // val bbL = bbLasts.toArray.sorted




    throw new NotImplementedError()
  }

  def findBBnum(BBs: Array[Int], idx: Int):Int = {
    BBs.search(idx) match {
      case InsertionPoint(insertionPoint) => insertionPoint-1
      case Found(foundIndex) => foundIndex
    }
  }
  //walkCFG: fill array of CFG nodes in reverse DFO
  // also fill: multi-entry, multi-exit, visited
  //

}
