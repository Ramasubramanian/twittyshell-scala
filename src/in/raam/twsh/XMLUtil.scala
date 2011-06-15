package in.raam.twsh

import xml.XML
import collection.mutable.ArrayBuffer

/**
 * Author : raam
 * Date   : 6/13/11
 * Time   : 3:34 PM
 */

object XMLUtil {

  def extractTweets(xmlStr: String) = {
    val tweetNodes = XML.loadString(xmlStr) \ "status"
    val tweets = new ArrayBuffer[Tweet]
    tweetNodes.foreach(tweets += Tweet(_))
    tweets.toArray
  }

  def extractTweet(xmlStr: String) = Tweet(XML.loadString(xmlStr))

  def extractUser(xmlStr: String) =  User(XML.loadString(xmlStr))

  def extractUsers(xmlStr: String) = {
    val userNodes = XML.loadString(xmlStr) \ "user"
    val users = new ArrayBuffer[User]
    userNodes.foreach(users += User(_))
    users.toArray
  }
}