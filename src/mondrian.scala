package net.ivoah.root

import scala.util.Random

import scalatags.Text.all.Frag
import scalatags.Text.implicits.*
import scalatags.Text.svgTags.{attr => _, *}
import scalatags.Text.svgAttrs.{frag => _, *}

def mondrian(w: Int = 512, h: Int = 512, lineWidth: Int = 5, colors: Seq[String] = Seq("white", "red", "blue", "yellow")): Frag = {
  case class Rect(x: Int, y: Int, w: Int, h: Int) {
    def attrs: Seq[AttrPair] = Seq(attr("x"):=x, attr("y"):=y, width:=w, height:=h)
    def rightLine = line(x1:=(x + w), y1:=y, x2:=(x + w), y2:=(y + h))
    def bottomLine = line(x1:=x, y1:=(y + h), x2:=(x + w), y2:=(y + h))
    def split(vertically: Boolean): (Rect, Rect) = {
      val at = if (vertically) Random.between(lineWidth, w - lineWidth) else Random.between(lineWidth, h - lineWidth)
      if (vertically) (Rect(x, y, at, h), Rect(x + at, y, w - at, h))
      else (Rect(x, y, w, at), Rect(x, y + at, w, h - at))
    }
  }

  def section(r: Rect, chance: Double = 1.0, vertical: Boolean = true): Seq[Frag] = {
    if (Random.nextDouble() < chance && r.w > lineWidth*2 && r.h > lineWidth*2) {
      r.split(vertical) match {
        case (left, right) => section(left, chance - 0.1, !vertical) ++ section(right, chance - 0.1, !vertical) ++ Seq(if (vertical) left.rightLine(stroke:="black") else left.bottomLine(stroke:="black"))
      }
    } else Seq(rect(fill:=Random.choice(colors), r.attrs))
  }

  svg(viewBox:=s"0 0 $w $h", width:=w, height:=h, xmlns:="http://www.w3.org/2000/svg", strokeWidth:=lineWidth, section(Rect(0, 0, w, h)))
}
