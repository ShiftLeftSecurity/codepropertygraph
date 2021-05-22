import java.io.File
import java.nio.file.Files
import java.security.{DigestInputStream, MessageDigest}
import scala.collection.JavaConverters._

object FileUtils {

  def listFilesRecursively(roots: File*): Seq[File] = {
    roots.flatMap { root =>
      Files.walk(root.toPath).iterator.asScala.map(_.toFile).filter(!_.isDirectory)
    }
  }

  def deleteRecursively(root: File): Unit = {
    if (root.exists) {
      Files.walk(root.toPath).iterator.asScala.map(_.toFile).collect {
        case file if (file.isDirectory) => deleteRecursively(file)
        case file => file.delete()
      }
    }
  }

  def md5(roots: File*): String = {
    val md = MessageDigest.getInstance("MD5")
    roots.foreach { root =>
      Files.walk(root.toPath).filter(!_.toFile.isDirectory).forEach { path =>
        val dis = new DigestInputStream(Files.newInputStream(path), md)
        // fully consume the inputstream
        while (dis.available > 0) {
          dis.read
        }
        dis.close
      }
    }
    md.digest.map(b => String.format("%02x", Byte.box(b))).mkString
  }

}
