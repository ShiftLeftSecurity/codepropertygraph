package io.shiftleft.codepropertygraph.cpgloading

import java.io.Closeable
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileSystem, FileSystems, FileVisitOption, FileVisitResult, Files, Path, Paths, SimpleFileVisitor}
import java.util.Collection as JCollection
import scala.collection.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*

class ZipArchive(inputFile: String) extends Closeable {
  private val zipFileSystem: FileSystem = FileSystems.newFileSystem(Paths.get(inputFile), null: ClassLoader)

  private def root: Path = zipFileSystem.getRootDirectories.iterator.next

  private def walk(rootPath: Path): Seq[Path] = {
    val entries = ArraySeq.newBuilder[Path]()
    Files.walkFileTree(
      rootPath,
      new SimpleFileVisitor[Path]() {
        override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
          if (attrs.isRegularFile)
            entries += file
          FileVisitResult.CONTINUE
        }
      }
    )
    entries.result()
  }

  def entries: Seq[Path] = walk(root)

  def getFileEntries: JCollection[Path] = entries.asJava

  override def close(): Unit = zipFileSystem.close()
}
