package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }
 
  /**
   * To test w/ curl 
   *
   * curl --header "Content-type: application/json" \
   *      --request POST --data '{"name": "mostlygeek"}' \
   *      http://localhost:9000/echoName
   * 
   * @return  Unknown
   */
  def echoName = Action(parse.json) { request => 
    (request.body \ "name").asOpt[String].map { name => 
      Ok("Hello: " + name) 
    }.getOrElse {
      BadRequest("Missing json value: [name]")
    }
  }
  
}
