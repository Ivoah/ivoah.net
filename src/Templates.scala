package net.ivoah.root

import scalatags.Text.all.*
import scalatags.Text.tags2.title
// import scalatags.Text.svgTags.svg

object Templates {
  def root(sites: Seq[String]) = doctype("html")(html(
    head(
      title(s"Noah Rosamilia"),

      link(rel:="stylesheet", href:="/static/style.css"),
      link(rel:="shortcut icon", `type`:="image/jpeg", href:="/static/favicon.jpg"),
    ),
    body(
      div(id:="fan",
        for ((site, i) <- sites.zipWithIndex) yield {
          a(href:=s"https://$site", style:=s"transform: rotate(${(i/sites.length.toDouble)*360}deg) translateX(100px);", site)
        }
      )
    )
  )).render
}
