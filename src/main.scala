package net.ivoah.root

import org.rogach.scallop.*
import net.ivoah.vial.*

import java.nio.file.Paths

@main
def main(rawArgs: String*): Unit = {
  class Args(args: Seq[String]) extends ScallopConf(args) {
    val host: ScallopOption[String] = opt[String](default = Some("0.0.0.0"))
    val port: ScallopOption[Int] = opt[Int](default = Some(4321))
    val socket: ScallopOption[String] = opt[String]()
    val verbose: ScallopOption[Boolean] = opt[Boolean](default = Some(false))
    val sitesPath: ScallopOption[String] = trailArg[String]()

    conflicts(socket, List(host, port))
    verify()
  }

  val args = Args(rawArgs)

  implicit val logger: String => Unit = if (args.verbose()) println else (msg: String) => ()
  val endpoints = Endpoints(Paths.get(args.sitesPath()))
  val server = args.socket.toOption match {
    case Some(path) =>
      println(s"Using unix socket: $path")
      Server(endpoints.router, path)
    case None =>
      println(s"Using host/port: ${args.host()}:${args.port()}")
      Server(endpoints.router, (args.host(), args.port()))
  }
  server.serve()
}
