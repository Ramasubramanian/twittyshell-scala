package in.raam.twsh


import collection.mutable.HashMap
import in.raam.twsh.TwitterCommands._

/**
 * Singleton decorator that adds additional logic for validating input arguments to Twitter
 * commands
 */
object TwshFunction {

  def toMap(args: Array[String]) = {
    val map = new HashMap[String, String]
    args.foreach(arg => {
      val t = arg.split("=")
      map.put(t(0), if(t.length > 1) t(1) else "")
    })
    map.toMap
  }

  def toMap(args: Array[(String, String)]) = {
    val map = new HashMap[String, String]
    args.foreach(t => map.put(t._1, t._2))
    map.toMap
  }

  def validate(params: Map[String, String], paramInfo: Map[String, String]): Boolean = {
    val mandatoryParams = paramInfo.filter(_._2.equals(REQUIRED))
    if(mandatoryParams.isEmpty) true
    else if (params.size >= mandatoryParams.size)
    	mandatoryParams.filter(x => params.contains(x._1)).size > 0
    else false
  }

  def getValidationMessage(paramInfo: Array[(String, String)]) =
    "Usage Parameters : "+paramInfo.map(x => x._1+"={"+x._2+"}").mkString(" ")

  def apply(function: (Array[String]) => String, paramInfo: Array[(String, String)]) = {
    (args: Array[String]) => {
      if(validate(toMap(args),toMap(paramInfo)))
    	function(args)
      else
    	getValidationMessage(paramInfo)
    }
  }

}