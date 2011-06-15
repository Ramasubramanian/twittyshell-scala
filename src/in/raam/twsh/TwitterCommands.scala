package in.raam.twsh

import in.raam.twsh.CredentialStore._
import in.raam.twsh.Constants._
import in.raam.twsh.{HttpRestAdapter => restAdapter}
import java.io.FileNotFoundException

/**
 * Singleton containing Twitter command objects as closures
 */
object TwitterCommands extends OAuthFunctionality {

  val REQUIRED = "required"
  val OPTIONAL = "optional" 
    
  val echo = (args: Array[String]) => args.mkString(" ")

  val timelineParams = Array(("page", OPTIONAL),("count", OPTIONAL))

  def timeLineCommand(url: String) = (args: Array[String]) => {
    val request = TwitterRequest(url,args)
    XMLUtil.extractTweets(restAdapter.get(request,oAuthConsumer)).mkString("\n")
  }

  val home = timeLineCommand(HOME_TIMELINE_URL)

  val retweets = timeLineCommand(TWITTER_RT_BY_ME_URL)

  val mentions = timeLineCommand(TWITTER_MENTIONS_URL)

  val rtofme = timeLineCommand(TWITTER_RT_OF_ME_URL)

  val followers = userCommand(TWITTER_FOLLOWERS_URL)

  val following = userCommand(TWITTER_FOLLOWING_URL)

  def userCommand(url: String) = (args: Array[String]) => {
    val request = TwitterRequest(url,args)
    XMLUtil.extractUsers(restAdapter.get(request,oAuthConsumer)).mkString("\n")
  }

  val tweetCmdParams = Array(("id",REQUIRED))

  val rmTweet = tweetOpCommand("Tweet removed successfully!",TWEET_DESTROY_URL)

  val reTweet = tweetOpCommand("Retweeted successfully!",RETWEET_URL)

  val friend = friendshipCommand("You are following this user now!",FOLLOW_URL)

  val unfriend = friendshipCommand("This user is not your friend anymore!",DONT_FOLLOW_URL)

  val favourite = tweetOpCommand("Tweet favourited successfully!",FAVOURITE_URL)

  def tweetOpCommand(msg: String,url : String) = (args: Array[String]) => {
    val request = DynaUrlTwitterRequest(url,args)
    msg + LS + XMLUtil.extractTweet(restAdapter.post(request,oAuthConsumer))
  }

  def friendshipCommand(msg: String, url: String) = (args: Array[String]) => {
    val request = DynaUrlTwitterRequest(url,args)
    try{
      msg + LS + XMLUtil.extractUser(restAdapter.post(request,oAuthConsumer))
    } catch {
      case f : FileNotFoundException => "No such user exists!"
    }
  }

  val tweet = (args: Array[String]) => {
    val text = args.mkString(" ")
    if(text.isEmpty)
      "Usage tweet <Status Text>"
    else if(text.length > 140)
      "Sorry Twitter does not allow anything more than 140 characters! Go for blogging instead!!!"
    else{
      val request = TwitterRequest(TWITTER_UPDATE_URL)
      request += ("include_entities","true")
      request += ("status",text)
      restAdapter.post(request,oAuthConsumer)
      "Tweet posted successfully!"
    }
  }

  def login = {
    print("Enter twitter user id to login:")
    val userId = readLine
    getKeyWithSecret(userId) match {
      case None => authorize(userId)
      case kws : Some[KeyWithSecret] => oAuthConsumer.setTokenWithSecret(kws.get.key,kws.get.secret)
    }
  }
}