import io.shiftleft.cpgserver.{CpgServerController, CpgServerSwagger, TestServerImpl, ResourcesApp}
import javax.servlet.ServletContext
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new CpgServerSwagger

  override def init(context: ServletContext): Unit = {
    context.mount(new CpgServerController(new TestServerImpl), "/*")
    context.mount(new ResourcesApp, "/api-docs")
  }

}
