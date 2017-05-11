package com.github.nadavwr.math

import scala.math._

object Vector2 {
  val zero: Vector2 = Vector2(0, 0)
  def polar(r: Double, theta: Double): Vector2 =
    apply(r*cos(theta), r*sin(theta))
}
case class Vector2(x: Double, y: Double) {
  def +(other: Vector2): Vector2 = Vector2(x + other.x, y + other.y)
  def -(other: Vector2): Vector2 = Vector2(x - other.x, y - other.y)
  def unary_-(): Vector2 = this * -1
  def *(scalar: Double): Vector2 = Vector2(x*scalar, y*scalar)
  def /(scalar: Double): Vector2 = Vector2(x/scalar, y/scalar)
  def r: Double = sqrt(x*x + y*y)
  def normalized: Vector2 = this / r
  def theta: Double =
    (atan2(y, x) + 2*Pi) % (2*Pi) // or just atan2(y, x)?
  def θ: Double = theta
  def cross(other: Vector2): Double = x*other.y - y*other.x
  def ⨯(other: Vector2): Double = cross(other)
  def dot(other: Vector2): Double = x*other.x + y*other.y
  def ⋅(other: Vector2): Double = dot(other)
}

