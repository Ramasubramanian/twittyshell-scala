package in.raam.twsh

import oauth.signpost.OAuthConsumer
import java.net.URL
import java.lang.Boolean

/**
 * Utility class to make http connections and get or post the xml response from/to twitter REST api
 * Author : raam
 * Date   : 6/13/11
 * Time   : 12:16 PM
 */

object HttpRestAdapter {

  def mkString(stream: java.io.InputStream) = scala.io.Source.fromInputStream(stream).mkString

   private def getResponse(request: TwitterRequest, consumer : OAuthConsumer, post: Boolean = false) = {
    val url =  new URL(request.urlString)
    val connection = url.openConnection
    if(post) connection.asInstanceOf[java.net.HttpURLConnection].setRequestMethod("POST")
    consumer.sign(connection)
    connection.connect()
    mkString(connection.getInputStream)
   }

  def get(request: TwitterRequest, consumer : OAuthConsumer) = getResponse(request,consumer)

  def post(request: TwitterRequest, consumer : OAuthConsumer) = getResponse(request,consumer,true)

}