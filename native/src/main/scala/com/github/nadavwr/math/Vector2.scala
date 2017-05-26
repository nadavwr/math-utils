package com.github.nadavwr.math

import com.github.nadavwr.ffi.FfiType

import scala.language.implicitConversions
import scala.scalanative.native._
import scala.scalanative.runtime.GC

object Vector2 extends Vector2Bindings[Vector2] {

  type Value = CStruct2[CDouble, CDouble]

  implicit val ffiTypeOfCVector: FfiType[Value] =
    FfiType.struct[Value]("Vector", FfiType[CDouble], FfiType[CDouble])

  override type Peer = Ptr[Value]
  override implicit def toPeer(v: Vector2): Peer = v.ptr
  override implicit def fromPeer(p: Peer): Vector2 = new Vector2(p)
  override def apply(x: Double, y: Double): Vector2 = {
    val ptr = GC.malloc_atomic(sizeof[Value]).cast[Ptr[Value]]
    !ptr._1 = x
    !ptr._2 = y
    new Vector2(ptr)
  }

  def unapply(v: Vector2): Option[(Double, Double)] =
    Some(v.x, v.y)
}

class Vector2 private[math] (private[math] val ptr: Ptr[Vector2.Value])
    extends Vector2Like[Vector2] {

  override def x: Double = !ptr._1
  override def y: Double = !ptr._2

  def copy(x: Double = this.x, y: Double = this.y): Vector2 =
    Vector2(x, y)

  override protected def bindings: Vector2Bindings[Vector2] = Vector2
}
