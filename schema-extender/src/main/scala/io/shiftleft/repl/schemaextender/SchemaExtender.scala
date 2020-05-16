package io.shiftleft.repl.schemaextender

import org.zeroturnaround.zip._
import better.files._
import overflowdb.codegen._

import sys.process._

/**
  * Allows ocular users to extend the cpg schema, by providing additional json files.
  * The user-provided json schema is are merged with the original cpg schema, and the generated nodefiles are compiled again.
  * Then, these are classes are overridden in the repl fat jar (io.shiftleft.repl-*.jar).
  * To enable the user to perform the same operation again, the original repl jar is backed up.
  *  */
object SchemaExtender extends App {
  val ocularInstallDir  = ".." // "/home/mp/bin/ocular"
  val schemaExtenderDir = s"$ocularInstallDir/schema-extender"
  val classesOutDir     = File(s"$schemaExtenderDir/generated/classes")

  val sources    = generateDomainClassSources
  val classFiles = compile(sources)
  updateReplJar(classFiles)
  println(
    "finished successfully - you can now start ocular as usual, you will have your new schema in place")

  lazy val replJarBackup: File = {
    val replJarBackup = File(s"$schemaExtenderDir/io.shiftleft.repl-backup.jar")
    if (!replJarBackup.exists) {
      findReplJar.copyTo(replJarBackup)
      println(s"backed up original repl.jar to $replJarBackup")
    }
    replJarBackup
  }

  lazy val findReplJar: File = {
    File(s"$ocularInstallDir/repl/lib").children
      .filter(_.name.startsWith("io.shiftleft.repl"))
      .toList match {
      case replJar :: Nil => replJar
      case Nil            => throw new AssertionError("unable to find the repl jar")
      case multiple =>
        throw new AssertionError(s"expected exactly *one* repl jar, found found $multiple")
    }
  }

  def generateDomainClassSources: Iterator[File] = {
    val inputSchemaFiles =
      File(s"$schemaExtenderDir/schemas").listRecursively.filter(_.name.endsWith(".json"))
    val generatedSrcDir  = File(s"$schemaExtenderDir/generated/src")
    val mergedSchemaFile = SchemaMerger.mergeCollections(inputSchemaFiles.map(_.toJava).toSeq)
    new CodeGen(
      mergedSchemaFile.getAbsolutePath,
      "io.shiftleft.codepropertygraph.generated"
    ).run(generatedSrcDir.toJava)
    generatedSrcDir.listRecursively.filter(_.isRegularFile)
  }

  def compile(sources: Iterator[File]): Iterator[File] = {
    classesOutDir.createDirectories.children.foreach(_.delete())
    val exitCode =
      s"""$ocularInstallDir/repl/bin/scalac -d $classesOutDir ${sources.mkString(" ")}""".!
    assert(exitCode == 0, s"error while invoking scalac. exit code was $exitCode")
    classesOutDir.listRecursively.filter(_.isRegularFile)
  }

  def updateReplJar(classFiles: Iterator[File]) = {
    val newZipEntries: Array[ZipEntrySource] = classFiles.map { classFile =>
      val pathInJar = classesOutDir.relativize(classFile).toString
      new FileSource(pathInJar, classFile.toJava)
    }.toArray

    val targetJar = findReplJar
    ZipUtil.addOrReplaceEntries(replJarBackup.toJava, newZipEntries, targetJar.toJava)
    println(s"added/replaced ${newZipEntries.size} class files in $targetJar")
  }

}
