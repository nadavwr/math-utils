package com.github.nadavwr.math

import jnr.ffi
import jnr.ffi.provider.jffi.NativeRuntime

import scala.language.implicitConversions

object Vector2 extends Vector2Bindings[Vector2] {
  override type Peer = Vector2Struct

  implicit def toPeer(v: Vector2): Peer = new Vector2Struct(v.x, v.y)
  implicit class ToPeer(v: Vector2) {
    def peer: Peer = toPeer(v)
  }
  override implicit def fromPeer(p: Peer): Vector2 = Vector2(p.x, p.y)

  override type Vector2Array = Array[Vector2]
  def tabulate(n: Int)(f: (Int) => Vector2): Vector2Array =
    Array.tabulate[Vector2](n)(f)
}

class Vector2Struct(_x: Double, _y: Double)
    extends ffi.Struct(NativeRuntime.getInstance()) {
  val xField = new Double()
  val yField = new Double()
  xField.set(_x)
  yField.set(_y)
  def x: scala.Double = xField.get()
  def y: scala.Double = yField.get()
}

case class Vector2(x: Double, y: Double)
    extends Vector2Like[Vector2] {

  override protected def bindings: Vector2Bindings[Vector2] = Vector2
}
