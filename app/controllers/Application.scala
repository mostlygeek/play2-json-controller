package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

case class Message(sender: String, message: String)
object Message {
  implicit object JsonReaderWriter extends Format[Message] {
    def reads(json: JsValue) = Message(
      (json \ "sender").as[String],
      (json \ "message").as[String]
    )
    def writes(msg: Message) = JsObject(Seq(
      "sender" -> JsString(msg.sender), 
      "message" -> JsString(msg.message)
    ))
  }
}

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }
 
  def echoName = Action(parse.json) { request => 
    (request.body \ "name").asOpt[String].map { name => 
      val json = Json.toJson(Map("name" -> name))
      Ok(json)
    }.getOrElse {
      BadRequest("Missing json value: [name]")
    }
  }

  def echoMsg = Action(parse.json) { request =>
    request.body.asOpt[Message].map { msg =>
      Ok(Json.toJson(Message("Play Framework", "echo: " + msg.message)))
    }.getOrElse {
      BadRequest("boom")
    }
  }
  
}
