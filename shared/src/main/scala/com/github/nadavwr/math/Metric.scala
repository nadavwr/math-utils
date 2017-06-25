package com.github.nadavwr.math

trait Metric {
  def mean: Double

  def count: Long

  def min: Long

  def max: Long

  def stddev: Double

  def +=(x: Long): Metric = put(x)
  def put(x: Long): Metric

}

object Metric {
  def apply(): Metric = new MetricImpl
}
