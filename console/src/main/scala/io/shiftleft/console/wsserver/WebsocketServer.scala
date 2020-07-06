package io.shiftleft.console.wsserver

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

import io.shiftleft.console.embammonite.{EmbeddedAmmonite, QueryResult}
import ujson.Obj

class WebsocketServer(ammonite: EmbeddedAmmonite) extends cask.MainRoutes {

  var openConnections = Set.empty[cask.WsChannelActor]
  val resultMap = new ConcurrentHashMap[UUID, QueryResult]()

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

  @cask.postJson("/query")
  def postQuery(query: String): Obj = {
    val uuid = ammonite.queryAsync(query) { result =>
      resultMap.put(result.uuid, result)
      openConnections.foreach { connection =>
        // Report on websocket connections that result is ready
        connection.send(cask.Ws.Text(result.uuid.toString))
      }
    }
    ujson.Obj("success" -> true, "uuid" -> uuid.toString)
  }

  @cask.get("/result/:uuidStr")
  def getResult(uuidStr: String): Obj = {
    val uuid = UUID.fromString(uuidStr)
    val result = resultMap.remove(uuid)
    ujson.Obj("success" -> true, "uuid" -> result.uuid.toString, "out" -> result.out, "err" -> result.err)
  }

  initialize()
}
