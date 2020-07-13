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

  @cask.get("/result/:uuidParam")
  def getResult(uuidParam: String): Obj = {
    var uuid: java.util.UUID = null
    try {
      uuid = UUID.fromString(uuidParam)
    } catch {
      case _: IllegalArgumentException => return ujson.Obj("success" -> false, "err" -> "UUID parameter is incorrectly formatted")
    }
    if (uuid == null) {
      return ujson.Obj("success" -> false, "err" -> "Internal Server Error")
    }

    val result = resultMap.remove(uuid)
    if (result == null) {
      ujson.Obj("success" -> false)
    } else {
      ujson.Obj("success" -> true, "uuid" -> result.uuid.toString, "out" -> result.out, "err" -> result.err)
    }
  }

  initialize()
}
