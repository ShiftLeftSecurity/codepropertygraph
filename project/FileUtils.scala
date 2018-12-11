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

  def md5(roots: List[File]): String = {
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

object CodeGenGlobalState {
  // this is very ugly, but I can't define it like that in the build.sbt
  var lastMd5: String = ""
}

object MergeSchemaTaskGlobalState {
  // this is very ugly, but I can't define it like that in the build.sbt
  var lastMd5: String = ""
}

object GenerateProtobufTaskGlobalState {
  // this is very ugly, but I can't define it like that in the build.sbt
  var lastMd5: String = ""
}
