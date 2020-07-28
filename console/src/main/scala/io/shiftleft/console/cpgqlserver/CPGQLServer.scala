package io.shiftleft.console.cpgqlserver

import java.util.Base64
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

import cask.model.{Request, Response}

import io.shiftleft.console.embammonite.{EmbeddedAmmonite, QueryResult}

class CPGQLServer(ammonite: EmbeddedAmmonite,
                  serverHost: String,
                  serverPort: Int,
                  serverAuthUsername: String = "",
                  serverAuthPassword: String = "") extends cask.MainRoutes {

  class basicAuth extends cask.RawDecorator{
    def wrapFunction(ctx: Request, delegate: Delegate) = {
      val authString = try {
        val authHeader = ctx.exchange.getRequestHeaders.get("authorization").getFirst
        val strippedHeader = authHeader.toString().replaceFirst("Basic ", "")
        new String(Base64.getDecoder.decode(strippedHeader))
      } catch {
        case _: Exception => ""
      }
      val Array(user, password): Array[String] = {
        val split = authString.split(":")
        if (split.length == 2) {
          Array(split(0).toString(), split(1).toString())
        } else {
          Array("", "")
        }
      }
      val isAuthorized = {
        (user == serverAuthUsername && password == serverAuthPassword) &&
        (serverAuthUsername != "" && serverAuthPassword != "")
      }
      delegate(Map("isAuthorized" -> isAuthorized))
    }
  }

  override def port: Int = {
    serverPort
  }

  override def host: String = {
    serverHost
  }

  var openConnections = Set.empty[cask.WsChannelActor]
  val resultMap = new ConcurrentHashMap[UUID, QueryResult]()
  val unauthorizedResponse = Response(ujson.Obj(), 401, headers = Seq("WWW-Authenticate" -> "Basic"))

  @cask.websocket("/connect")
  def handler(): cask.WebsocketResult = {
    cask.WsHandler { connection =>
      connection.send(cask.Ws.Text("connected"))
      openConnections += connection
      cask.WsActor {
        case cask.Ws.Close(_, _) => openConnections -= connection
      }
    }
  }

  @basicAuth()
  @cask.postJson("/query")
  def postQuery(query: String)(isAuthorized: Boolean) = {
    val res = if(!isAuthorized) {
      unauthorizedResponse
    } else {
      val uuid = ammonite.queryAsync(query) { result =>
        resultMap.put(result.uuid, result)
        openConnections.foreach { connection =>
          // Report on websocket connections that result is ready
          connection.send(cask.Ws.Text(result.uuid.toString))
        }
      }
      Response(ujson.Obj("success" -> true, "uuid" -> uuid.toString), 200)
    }
    res
  }

  @basicAuth()
  @cask.get("/result/:uuidParam")
  def getResult(uuidParam: String)(isAuthorized: Boolean) = {
    val res = if (!isAuthorized) {
      unauthorizedResponse
    }  else {
      val uuid = try {
        UUID.fromString(uuidParam)
      } catch {
        case _: IllegalArgumentException => null
      }
      val finalRes = if (uuid == null) {
        ujson.Obj("success" -> false, "err" -> "UUID parameter is incorrectly formatted")
      } else {
        val resFromMap = resultMap.remove(uuid)
        if (resFromMap == null) {
          ujson.Obj("success" -> false, "err" -> "No result found for specified UUID")
        } else {
          ujson.Obj("success" -> true,
            "uuid" -> resFromMap.uuid.toString,
            "stdout" -> resFromMap.out,
            "stderr" -> resFromMap.err)
        }
      }
      Response(finalRes, 200)
    }
    res
  }

  initialize()
}

