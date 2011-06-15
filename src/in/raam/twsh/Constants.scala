package in.raam.twsh

/**
 * Contains all the constants used in TwittyShell App
 */
object Constants {

  val LS = System.getProperty("line.separator")
  val TWITTER_REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token"
  val TWITTER_ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token"
  val TWITTER_AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize"
  val HOME_TIMELINE_URL = "http://api.twitter.com/1/statuses/home_timeline.xml"
  val TWITTER_MENTIONS_URL = "http://api.twitter.com/1/statuses/mentions.xml"
  val TWITTER_RT_OF_ME_URL = "http://api.twitter.com/1/statuses/retweets_of_me.xml"
  val TWITTER_RT_BY_ME_URL = "http://api.twitter.com/1/statuses/retweeted_by_me.xml"
  val TWITTER_FOLLOWERS_URL = "http://api.twitter.com/1/statuses/followers.xml"
  val TWITTER_FOLLOWING_URL = "http://api.twitter.com/1/statuses/friends.xml"
  val TWITTER_UPDATE_URL = "http://api.twitter.com/1/statuses/update.xml"
  val TWEET_DESTROY_URL = "http://api.twitter.com/1/statuses/destroy/${id}.xml"
  val RETWEET_URL = "http://api.twitter.com/1/statuses/retweet/${id}.xml"
  val FOLLOW_URL = "http://api.twitter.com/1/friendships/create/${id}.xml"
  val DONT_FOLLOW_URL = "http://api.twitter.com/1/friendships/destroy/${id}.xml"
  val FAVOURITE_URL = "http://api.twitter.com/1/favorites/create/${id}.xml"
}