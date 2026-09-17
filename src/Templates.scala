package net.ivoah.root

import scalatags.Text.all.*
import scalatags.Text.tags2.title
import java.net.URLEncoder

object Templates {
  def page(_title: String)(_body: Frag*): String = doctype("html")(html(
    head(
      title(s"Noah Rosamilia"),

      link(rel:="stylesheet", href:="/static/style.css"),
      link(rel:="shortcut icon", `type`:="image/jpeg", href:="/static/favicon.jpg"),
    ),
    body(_body)
  )).render

  def root(sites: Seq[String]) = page("Noah Rosamilia")(
    div(cls:="center",
      img(src:="/mondrian.svg?w=256&h=256"),
      div(id:="name", "Noah", br(), "Rosamilia"),
      div(id:="fan",
        for ((site, i) <- sites.zipWithIndex) yield {
          a(href:=s"https://$site", style:=s"transform: translateY(-1em) rotate(${(i/sites.length.toDouble)*360}deg) translateX(133px);", site)
        }
      )
    )
  )

  def mondrian(w: Int, h: Int, lineWidth: Int, colors: Seq[String], adding: Option[Boolean], removing: Option[Int]): String = {
    val newColors = colors.zipWithIndex.collect {
      case (c, i) if !removing.exists(_ == i) => c
    } ++ (if (adding.exists(identity)) Some("white") else None)
    page("Mondrian art generator")(
      form(method:="POST", textAlign.left,
        label("Width: ", input(`type`:="number", name:="w", value:=w)), br(),
        label("Height: ", input(`type`:="number", name:="h", value:=h)), br(),
        label("Line width: ", input(`type`:="number", name:="lineWidth", value:=lineWidth)), br(),
        "Colors: ", ul(
          for ((color, i) <- newColors.zipWithIndex) yield li(button(name:="removing", value:=i, raw("&times;")), input(`type`:="color", name:="colors", value:=color)),
          li(button(name:="adding", value:="true", "Add"))
        ),
        button("Generate")
      ),
      img(src:=s"/mondrian.svg?${Seq(
        "w" -> w,
        "h" -> h,
        "lineWidth" -> lineWidth,
        "colors" -> newColors.mkString(",")
      ).map{case (k, v) => s"$k=${URLEncoder.encode(v.toString, "utf-8")}"}.mkString("&")}")
    )
  }
}
