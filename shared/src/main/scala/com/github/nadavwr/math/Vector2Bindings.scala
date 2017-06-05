package com.github.nadavwr.math

import scala.language.implicitConversions
import scala.math._

trait Vector2Bindings[Vector2 <: Vector2Like[Vector2]] {
  type Peer
  //implicit def toPeer(v: Vector2): Peer
  implicit def fromPeer(p: Peer): Vector2

  def apply(x: Double, y: Double): Vector2
  def unapply(v: Vector2): Option[(Double, Double)]

  type Vector2Array
  //def tabulate(n: Int)(f: Int => Vector2): Vector2Array

  lazy val zero: Vector2 = apply(0d, 0d)

  def polar(r: Double, theta: Double): Vector2 =
    apply(r*cos(theta), r*sin(theta))

}
