package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.Json

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
  
}
