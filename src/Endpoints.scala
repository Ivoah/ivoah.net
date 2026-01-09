package net.ivoah.root

import net.ivoah.vial.*
import java.io.File
import java.nio.file.{Path, Paths}

def timeit[T](name: String)(fn: => T): T = {
  val t0 = System.currentTimeMillis()
  val ret = fn
  val t1 = System.currentTimeMillis()
  println(s"$name took ${(t1 - t0)/1000.0} seconds")
  ret
}

class Endpoints(sitesPath: Path) {
  def router: Router = Router {
    case ("GET" , "/", _) =>
      val sites = sitesPath.toFile.listFiles.map(_.getName).sorted
      Response(Templates.root(sites))
    case ("GET", s"/static/$file", _) => Response.forFile(Paths.get("static"), Paths.get(file))
  }
}
