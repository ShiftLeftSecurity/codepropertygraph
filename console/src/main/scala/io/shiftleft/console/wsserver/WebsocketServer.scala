package io.shiftleft.console.wsserver

class WebsocketServer extends cask.MainRoutes {

  @cask.websocket("/connect/:userName")
  def handler(userName: String): cask.WebsocketResult = {
    cask.WsHandler { channel =>
      cask.WsActor {
        case cask.Ws.Text("") => channel.send(cask.Ws.Close())
        case cask.Ws.Text(data) =>
          channel.send(cask.Ws.Text(userName + " " + data))
      }
    }
  }

  initialize()
}
