package com.github.nadavwr.math

import com.github.nadavwr.ffi.FfiType

import scala.language.implicitConversions
import scala.scalanative.native._

object Vector2 extends Vector2Bindings[Vector2] {

  type Value = CStruct2[CDouble, CDouble]

  val ffiTypeOfVector: FfiType[Value] =
    FfiType.struct[Value]("Vector", FfiType[CDouble], FfiType[CDouble])

  override type Peer = Ptr[Value]
  implicit def toPeer(v: Vector2)
                     (implicit zone: Zone): Peer = {
    val ptr = zone.alloc(sizeof[Value]).cast[Peer]
    !ptr._1 = v.x
    !ptr._2 = v.y
    ptr
  }
  implicit class ToPeer(v: Vector2) {
    def peer(implicit zone: Zone): Peer = toPeer(v)(zone)
  }
  override implicit def fromPeer(p: Peer): Vector2 = {
    val x = !p._1
    val y = !p._2
    Vector2(x, y)
  }

  override type Vector2Array = Peer
  def tabulate(n: Int)
              (f: (Int) => Vector2)
              (implicit zone: Zone): Vector2Array = {
    val array = zone.alloc(n * sizeof[Value]).cast[Peer]
    for (i <- 0 until n) {
      val v = f(i)
      val ptr = array + i
      !ptr._1 = v.x
      !ptr._2 = v.y
    }
    array
  }
}

case class Vector2(override val x: Double,
                   override val y: Double)
    extends Vector2Like[Vector2] {

  override protected def bindings: Vector2Bindings[Vector2] = Vector2
}
