package io.shiftleft.console.wsserver

import ujson.Obj

class WebsocketServer extends cask.MainRoutes {

  var openConnections = Set.empty[cask.WsChannelActor]

  @cask.websocket("/connect")
  def handler(): cask.WebsocketResult = {
    cask.WsHandler { connection =>
      connection.send(cask.Ws.Text("connected"))
      openConnections += connection
      cask.WsActor { case cask.Ws.Close(_, _) => openConnections -= connection }
    }
  }

  @cask.postJson("/query")
  def postQuery(query: String): Obj = {
    println("received: " + query)
    ujson.Obj("success" -> true, "err" -> "")
  }

  @cask.get("/result/:uuid")
  def getResult(uuid: String): Obj = {
    println("Fetching result for: " + uuid)
    ujson.Obj("success" -> true, "err" -> "")
  }

  initialize()
}
