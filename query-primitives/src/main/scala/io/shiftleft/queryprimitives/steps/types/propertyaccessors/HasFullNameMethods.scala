package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import java.util.regex.Pattern

import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.language.higherKinds

class HasFullNameMethods[PipeType[+_], ElemType <: nodes.HasFullName](val pipe: PipeType[ElemType]) extends AnyVal {

  def fullName(implicit ops: PipeOperations[PipeType]): PipeType[String] = {
    pipe.map(_.fullName)
  }

  def fullName(regex: String)
              (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    val regexPredicate = Pattern.compile(regex).asPredicate()
    pipe.filter(hasFullName => regexPredicate.test(hasFullName.fullName))
  }

  def fullName(regex: String*)
              (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    val regexPredicates = regex.map(regex => Pattern.compile(regex).asPredicate())
    pipe.filter(hasFullName => regexPredicates.exists(_.test(hasFullName.fullName)))
  }

  def fullNameExact(value: String)
                   (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    pipe.filter(_.fullName == value)
  }

  def fullNameExact(value: String*)
                   (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    pipe.filter(hasFullName => value.contains(hasFullName.fullName))
  }

  def fullNameNot(regex: String)
                 (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    val regexPredicate = Pattern.compile(regex).asPredicate()
    pipe.filter(hasFullName => !regexPredicate.test(hasFullName.fullName))
  }

  def fullNameNot(regex: String*)
                 (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    val regexPredicates = regex.map(regex => Pattern.compile(regex).asPredicate())
    pipe.filter(hasFullName => !regexPredicates.exists(_.test(hasFullName.fullName)))
  }

}
