package net.ivoah.root

import scala.util.Random

extension (r: Random.type) {
  def choice[T](seq: Seq[T]): T = seq(r.between(0, seq.length))
}
