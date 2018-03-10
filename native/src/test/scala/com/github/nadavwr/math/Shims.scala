package com.github.nadavwr.math

import scala.language.implicitConversions
import scala.scalanative.native._

trait Shims {
  // TODO: add implicit zone for native
  implicit def zone: Zone = new Zone {
    override def alloc(size: CSize): Ptr[Byte] =
      stdlib.malloc(size)
  }
}
