package in.raam.twsh

import collection.mutable.{HashMap, Map}
import in.raam.twsh.Constants._
import io.Source
import java.io.{FileWriter, File}

/**
 * User: raam
 * Date: 6/5/11
 * Time: 11:22 AM
 */

case class KeyWithSecret(key: String, secret: String)

object KeyWithSecret{
  def apply(str: String) = {
    val arr = str.split(",")
    new KeyWithSecret(arr(0),arr(1))
  }
}

object CredentialStore {

  private val map = new HashMap[String,KeyWithSecret]

  val fName = System.getProperty("user.home") + java.io.File.separator + ".twsh-scala"

  val file = new java.io.File(fName)

  if(!file.exists()) file.createNewFile()

  Source.fromFile(fName).getLines().foreach(s => {
    val str = s.split("=")
    map.put(str(0),KeyWithSecret(str(1)))
  })


  def getKeyWithSecret(userId: String) = map.get(userId)

  def addKeyWithSecret(userId: String, key: String, secret: String) = map.put(userId,new KeyWithSecret(key,secret))

  def save = {
    val file = new FileWriter(fName)
    map.foreach( (t)=> file.write(t._1+"="+t._2.key+","+t._2.secret+LS))
    file.close
  }

}