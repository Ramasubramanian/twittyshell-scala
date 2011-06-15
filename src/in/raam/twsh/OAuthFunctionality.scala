package in.raam.twsh

import in.raam.twsh.Constants._
import oauth.signpost.OAuth
import in.raam.twsh.CredentialStore._
import oauth.signpost.basic.{DefaultOAuthConsumer, DefaultOAuthProvider}

/**
 * Trait encapsulating OAuth utility methods from sign post API
 */
trait OAuthFunctionality {

  var oAuthProvider = new DefaultOAuthProvider(TWITTER_REQUEST_TOKEN_URL,TWITTER_ACCESS_TOKEN_URL,TWITTER_AUTHORIZE_URL)

  var oAuthConsumer = new DefaultOAuthConsumer("xxxxxxxxxxxxxxxxxxxxx","xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

  def authorize(userName : String) = {
    val authUrl = oAuthProvider.retrieveRequestToken(oAuthConsumer,OAuth.OUT_OF_BAND)
    println("Please visit:\n"
                        + authUrl
                        + "\n... in your browser and grant TwittyShell authorization")
    println("Enter the PIN code and hit ENTER when you're ready to type commands for twitter!")
    val pin = readLine
    oAuthProvider.retrieveAccessToken(oAuthConsumer,pin)
    addKeyWithSecret(userName,oAuthConsumer.getToken,oAuthConsumer.getTokenSecret)
  }



}