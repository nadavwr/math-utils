package com.github.nadavwr.math

import com.github.nadavwr.ffi._

import scala.language.implicitConversions
import scala.scalanative.native._
import scala.scalanative.runtime.GC

object CVector { cvector =>
  def apply(ptr: Ptr[Value]): CVector = new CVector(ptr)

  type Value = CStruct2[CDouble, CDouble]
  def apply(x: Double, y: Double): CVector = {
    val ptr = GC.malloc_atomic(sizeof[CVector.Value]).cast[Ptr[CVector.Value]]
    val vector = new CVector(ptr)
    vector.x = x
    vector.y = y
    vector
  }
  trait Implicits {
    implicit val ffiTypeOfCVector: FfiType[CVector.Value] =
      FfiType.struct[CVector.Value]("Vector", FfiType[CDouble], FfiType[CDouble])
    implicit def fromVector(vector: Vector2): CVector =
      cvector.fromVector(vector)
    implicit def toVector(vector: CVector): Vector2 =
      cvector.toVector(Vector2(vector.x, vector.y))
  }
  def fromVector(vector: Vector2): CVector = CVector(vector.x, vector.y)
  def toVector(vector: CVector): Vector2 = Vector2(vector.x, vector.y)
}

class CVector(val ptr: Ptr[CVector.Value]) {
  def x: Double = !ptr._1
  def y: Double = !ptr._2

  def x_=(x: Double): Unit = !ptr._1 = x
  def y_=(y: Double): Unit = !ptr._2 = y

  def :=(other: Vector2): Unit = {
    x = other.x
    y = other.y
  }
}

