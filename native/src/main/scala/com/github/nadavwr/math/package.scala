package com.github.nadavwr

import com.github.nadavwr.ffi.FfiType

package object math extends MathFunctions {
  implicit def ffiTypeOfVector: FfiType[Vector2.Value] =
    Vector2.ffiTypeOfVector
}
