package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

/**
 * This case class holds the json message from the client. 
 *
 * In order to convert a blob of json into a case class
 * we need a custom reader/writer for it. The conversion by 
 * Play's JSON library is done implicitly. 
 *
 * This is the pattern recommended in the docs. Create a
 * case class and then manually create reads/writes functions
 * to convert it.
 *
 * ... it would be nice to use some reflections here to 
 * automatically do simple conversions... 
 */
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
 
  /**
   * the endPoint for the /echoName JSON ajax call 
   */
  def echoName = Action(parse.json) { request => 
    (request.body \ "name").asOpt[String].map { name => 
      val json = Json.toJson(Map("name" -> name))
      Ok(json)
    }.getOrElse {
      BadRequest(Json.toJson(Map("err" -> "missing [name] field")))
    }
  }

  /**
   * the end point for the /echoMsg JSON ajax call
   */
  def echoMsg = Action(parse.json) { request =>
    // notice the asOpt[Message] ... automatic conversion to a case class..
    request.body.asOpt[Message].map { msg =>
      Ok(Json.toJson(Message("Play Framework", "echo: " + msg.message)))
    }.getOrElse {
      BadRequest(Json.toJson(Map("err" -> "invalid message format")))
    }
  }
  
}
