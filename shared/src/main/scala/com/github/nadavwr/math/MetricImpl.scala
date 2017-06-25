package com.github.nadavwr.math

import scala.math.sqrt

private class MetricImpl extends Metric {
  self: Metric =>
  var count: Long = 0
  var mean: Double = 0
  var min: Long = Long.MaxValue
  var max: Long = Long.MinValue
  var M2: Double = 0

  def stddev: Double = sqrt(M2 / count)

  def put(x: Long): Metric = {
    val delta = x - mean
    mean += delta / (count+1)
    M2 += delta * (x-mean)
    if (x < min) min = x
    if (x > max) max = x
    count += 1
    this
  }

  def kind: String = "Metric"
  override def toString: String =
    s"$kind(count: $count, μ: $mean, σ: $stddev, min: $min, max: $max)"
}
