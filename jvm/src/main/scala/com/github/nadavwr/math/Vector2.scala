package com.github.nadavwr.math

import com.sun.jna.Structure

import scala.collection.JavaConverters._
import scala.language.implicitConversions

object Vector2 extends Vector2Bindings[Vector2] {
  override type Peer = Vector2

  override implicit def toPeer(v: Vector2): Peer = v
  override implicit def fromPeer(p: Peer): Vector2 = p

  private[Vector2] lazy val fieldOrder: java.util.List[String] =
    List("x", "y").asJava
}

case class Vector2(override val x: Double,
                   override val y: Double)
  extends Structure
    with Structure.ByValue
    with Vector2Like[Vector2] {
  override def getFieldOrder: java.util.List[String] = Vector2.fieldOrder
  override protected def bindings: Vector2Bindings[Vector2] = Vector2
}
