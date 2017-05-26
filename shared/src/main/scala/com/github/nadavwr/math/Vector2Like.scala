package com.github.nadavwr.math

import scala.language.implicitConversions
import scala.math._

trait Vector2Like[Vector2 <: Vector2Like[Vector2]] {
  this : Vector2 =>

  protected def bindings: Vector2Bindings[Vector2]

  def x: Double
  def y: Double

  def +(other: Vector2): Vector2 = bindings.apply(x + other.x, y + other.y)
  def -(other: Vector2): Vector2 = bindings.apply(x - other.x, y - other.y)
  def unary_-(): Vector2 = this * -1
  def *(scalar: Double): Vector2 = bindings.apply(x*scalar, y*scalar)
  def /(scalar: Double): Vector2 = bindings.apply(x/scalar, y/scalar)
  def r: Double = sqrt(x*x + y*y)
  def normalized: Vector2 = this / r
  def theta: Double =
    atan2(y, x) // or (atan2(y, x) + 2*Pi) % (2*Pi)?
  def θ: Double = theta
  def cross(other: Vector2): Double = x*other.y - y*other.x
  def ⨯(other: Vector2): Double = cross(other)
  def dot(other: Vector2): Double = x*other.x + y*other.y
  def ⋅(other: Vector2): Double = dot(other)

  def toPair[I : Integral]: (I, I) = {
    val integral = implicitly[Integral[I]]
    (integral.fromInt(x.toInt), integral.fromInt(y.toInt))
  }
}
