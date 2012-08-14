package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }
 
  def echoName = Action(parse.json) { request => 
    (request.body \ "name").asOpt[String].map { name => 
      Ok(name)
    }.getOrElse {
      BadRequest("Missing json value: [name]")
    }
  }
  
}
