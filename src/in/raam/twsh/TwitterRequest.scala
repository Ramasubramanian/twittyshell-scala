package in.raam.twsh

import collection.mutable.HashMap

/**
 * Author : raam
 * Date   : 6/13/11
 * Time   : 12:20 PM
 */

class TwitterRequest(url: String) {

  protected val paramMap = new HashMap[String,String]()

  def +=(paramKey: String, paramValue: String) = paramMap.put(paramKey,paramValue)

  def +=(paramStr: String) = {
      val arr = paramStr.split("=")
      paramMap.put(arr(0),if(arr.length > 0) arr(1) else "")
  }

  def ++(params: Array[String]) = params.foreach(+=(_))

  def urlString = {
    val paramStr = paramMap.foldLeft(url+"?")((x: String,y:(String,String)) => {
      x + y._1 + "=" + java.net.URLEncoder.encode(y._2,"UTF-8") + "&"
    })
    paramStr.dropRight(1)
  }

}

class DynaUrlTwitterRequest(url: String) extends TwitterRequest(url: String) {
  def replaceParams(url: String) : String = {
    val start = url.indexOf("${")
    val end = url.indexOf("}")
    if(start > -1 && end > -1){
      val key = url.substring(start + 2, end)
      replaceParams(url.substring(0,start) + paramMap.getOrElse(key,"") + url.substring(end+1))
    } else url

  }
  override def urlString = replaceParams(url)
}

object TwitterRequest{
  def apply(url : String, params : Array[String] = Array()) = {
    val request = new TwitterRequest(url)
    request ++ params
    request
  }
}

object DynaUrlTwitterRequest{
  def apply(url : String, params : Array[String] = Array()) = {
    val request = new DynaUrlTwitterRequest(url)
    request ++ params
    request
  }
}
