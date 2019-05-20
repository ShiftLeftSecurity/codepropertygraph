package io.shiftleft.cpgserver

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import org.slf4j.LoggerFactory

object JettyLauncher {

  def startServer(port : Int) : Unit  = {
    val logger = LoggerFactory.getLogger(getClass)
    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[DefaultServlet], "/")

    server.setHandler(context)

    try {
      server.start
    } catch {
      case _: java.net.BindException => {
        logger.warn(s"Not starting server - port $port is occupied")
        System.exit(1)
      }
    }
    server.join
  }

}
