package net.ivoah.root

import scalatags.Text.all.*
import scalatags.Text.tags2.title

object Templates {
  def root(sites: Seq[String]) = doctype("html")(html(
    head(
      title(s"Noah Rosamilia"),

      link(rel:="stylesheet", href:="/static/style.css"),
      link(rel:="shortcut icon", `type`:="image/jpeg", href:="/static/favicon.jpg"),
    ),
    body(
      img(src:="/mondrian?w=256&h=256"),
      div(id:="name", "Noah", br(), "Rosamilia"),
      div(id:="fan",
        for ((site, i) <- sites.zipWithIndex) yield {
          a(href:=s"https://$site", style:=s"transform: translateY(-1em) rotate(${(i/sites.length.toDouble)*360}deg) translateX(133px);", site)
        }
      )
    )
  )).render
}
