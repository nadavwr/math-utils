package com.github.nadavwr.math

trait Timer extends Metric {
  def time[A](f: => A): A

  def start(): Timing
}

object Timer {
  def apply(measure: () => Long): Timer = new TimerImpl(measure)

  def millis: Timer = apply(System.currentTimeMillis)

  def nanos: Timer = apply(System.nanoTime)
}
