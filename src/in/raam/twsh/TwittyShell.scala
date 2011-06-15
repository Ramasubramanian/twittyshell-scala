
import in.raam.twsh.{TwshFunction,CredentialStore}
import scala.collection.mutable._
import in.raam.twsh.TwitterCommands._

/**
 * Main class for TwittyShell App
 */
object TwittyShell extends App {

  val SIGTERM = "quit"

  val commandMap = new HashMap[String, (Array[String]) => String]

  def readInput: Unit = {
    while (true){
      print("twittyshell> ")
      readLine match {
        case SIGTERM => CredentialStore.save; return
        case line: String => try {
          println(processInput(line))
        } catch {
          case e: Exception => e.printStackTrace
        }
      }
    }
  }

  def processInput(input: String) = {
    val arr = input.split("[\\s]+")
    commandMap.get(arr(0)) match {
      case None => "Invalid Command : List of valid commands are <" + commandMap.keySet.mkString(",") + ">"
      case func: Some[(Array[String]) => String] => func.get(arr.drop(1))
    }
  }

  val EMPTY_ARRAY = Array(("", ""))

  commandMap += ("echo", TwshFunction(echo, EMPTY_ARRAY)(_))
  commandMap += ("home", TwshFunction(home, timelineParams)(_))
  commandMap += ("retweets", TwshFunction(retweets, timelineParams)(_))
  commandMap += ("mentions", TwshFunction(mentions, timelineParams)(_))
  commandMap += ("rtofme", TwshFunction(rtofme, timelineParams)(_))
  commandMap += ("followers", TwshFunction(followers, EMPTY_ARRAY)(_))
  commandMap += ("following", TwshFunction(following, EMPTY_ARRAY)(_))
  commandMap += ("tweet", tweet(_))
  commandMap += ("rm", TwshFunction(rmTweet, tweetCmdParams)(_))
  commandMap += ("rt", TwshFunction(reTweet, tweetCmdParams)(_))
  commandMap += ("follow", TwshFunction(friend, tweetCmdParams)(_))
  commandMap += ("dontfollow", TwshFunction(unfriend, tweetCmdParams)(_))
  commandMap += ("favit", TwshFunction(favourite, tweetCmdParams)(_))

  //main code
  login
  readInput

}