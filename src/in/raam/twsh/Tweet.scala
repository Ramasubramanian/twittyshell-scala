package in.raam.twsh

import in.raam.twsh.Constants._

trait Printable {

  val line = "-" * 80 + LS

  def printAttributes(attributes : Array[(String,String)]) = attributes.map(x => x._1 + " : "+ x._2).mkString(" | ")
}

/**
 * Author : raam
 * Date   : 6/13/11
 * Time   : 3:34 PM
 */

class Tweet(id: String, text: String, created_at : String, user: User)  extends Printable{
  override def toString = line + text + LS + printAttributes(attributes) + LS + line

  val attributes = Array(("By",user.name),("Id",id),("At",created_at))

}

object Tweet{
  def apply(node: scala.xml.NodeSeq) = {
    new Tweet(
      (node \ "id").text,
      (node \ "text").text,
      (node \ "created_at").text,
      User(node \ "user")
    )
  }
}

case class User(id: String, name: String, screen_name:String, followers_count: String, created_at: String,
           friends_count: String, description: String, location : String) extends Printable{

  override def toString = line + printAttributes(nameAttributes) + LS + printAttributes(descAttributes) + LS+
                            printAttributes(friendsAttributes) + LS + line

  val nameAttributes = Array(("Name",name),("Screen Name",screen_name),("Id",id),("At", created_at))
  val descAttributes = Array(("From",location),("Description",description))
  val friendsAttributes = Array(("Followers",followers_count),("Following",friends_count))
}

object User{
  def apply(node: scala.xml.NodeSeq) = {
    new User(
      (node \ "id").text,
      (node \ "name").text,
      (node \ "screen_name").text,
      (node \ "followers_count").text,
      (node \ "created_at").text,
      (node \ "friends_count").text,
      (node \ "description").text,
      (node \ "location").text
    )
  }
}