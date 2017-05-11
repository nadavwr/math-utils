package com.github.nadavwr

import scala.math._

package object math {
  implicit class NumericExt[N : Numeric](n: N) {
    private val numeric = implicitly[Numeric[N]]
    import numeric._

    def clamp(a: N, b: N): N = {
      val minVal = min(a, b)
      val maxVal = max(a, b)
      min(max(n, minVal), maxVal)
    }

    def ~=(b: N)(precision: N): Boolean =
      lteq(abs(n - b), precision * b)
  }

  def acosh(x: Double): Double =
    log(x + sqrt(pow(x, 2) - 1))
}

