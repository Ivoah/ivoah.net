package net.ivoah.root

import net.ivoah.vial.*
import java.nio.file.{Path, Paths}

class Endpoints(sitesPath: Path) {
  def router: Router = Router {
    case ("GET" , "/", _) =>
      val sites = sitesPath.toFile.listFiles.map(_.getName).sorted.toSeq
      Response(Templates.root(sites))
    case ("GET", "/mondrian", r) =>
      val w = r.params.get("w").flatMap(_.toIntOption).getOrElse(512)
      val h = r.params.get("h").flatMap(_.toIntOption).getOrElse(512)
      val lineWidth = r.params.get("lineWidth").flatMap(_.toIntOption).getOrElse(5)
      val colors = r.params.get("colors").map(_.split(",").toSeq).getOrElse(Seq("white", "red", "blue", "yellow"))
      Response(mondrian(w, h, lineWidth, colors).render, headers = Map("Content-Type" -> Seq("image/svg+xml")))
    case ("GET", s"/static/$file", _) => Response.forFile(Paths.get("static"), Paths.get(file))
  }
}
