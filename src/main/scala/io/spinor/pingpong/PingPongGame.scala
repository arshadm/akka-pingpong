package io.spinor.pingpong

import java.net.InetAddress

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

/**
  * The Ping/Pong game application.
  *
  * @author A. Mahmood (arshadm@spinor.io)
  */
object PingPongGame extends App {
  final val logger = LoggerFactory.getLogger("PingPongGame")

  val isMaster = "true".equals(System.getenv("MASTER"))

  logger.info("Master at IP address: " + InetAddress.getByName("master"))

  if (isMaster) {
    val system = ActorSystem("RemoteSystem", ConfigFactory.load("application-master"))
    val remotePlayer = system.actorOf(Props[RemotePlayerActor], name = "RemoteActor")
  } else {
    Thread.sleep(5000)
    val system = ActorSystem("LocalSystem", ConfigFactory.load("application-slave"))
    val localPlayer = system.actorOf(Props[LocalPlayerActor], name = "LocalActor") // the local actor
  }
}

/**
  * The remote player actor.
  */
class RemotePlayerActor extends Actor {
  final val logger = LoggerFactory.getLogger(classOf[RemotePlayerActor])

  def receive = {
    case "ping" =>
      logger.info(s"ping")
      sender ! "pong"
    case "pong" =>
      logger.info(s"pong")
      sender ! "ping"
  }
}

/**
  * The local player actor.
  */
class LocalPlayerActor extends Actor {
  final val logger = LoggerFactory.getLogger(classOf[LocalPlayerActor])

  val remote = context.actorSelection("akka.tcp://RemoteSystem@master:5150/user/RemoteActor")
  logger.info("Sending initial message to master")
  Thread.sleep(5000)
  remote ! "ping"

  def receive = {
    case "ping" =>
      logger.info(s"ping")
      sender ! "pong"
    case "pong" =>
      logger.info(s"pong")
      sender ! "ping"
  }
}
