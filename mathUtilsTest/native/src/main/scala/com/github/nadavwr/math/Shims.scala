package com.github.nadavwr.math

import scala.scalanative.native.Alloc
import scala.language.implicitConversions

trait Shims {
  implicit def alloc: Alloc = Alloc.system
}
