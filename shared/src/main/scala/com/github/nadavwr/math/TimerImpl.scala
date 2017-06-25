package com.github.nadavwr.math

private class TimerImpl(measure: () => Long) extends MetricImpl with Timer {
  self: Metric =>

  override def time[A](f: => A): A = {
    val t0 = measure()
    val a = f
    val dt = measure() - t0
    put(dt)
    a
  }

  var timingStart: Long = 0

  override def start(): Timing = {
    timingStart = measure()
    new Timing {
      override def stop(): Timer = {
        put(measure() - timingStart)
        self
      }
    }
  }

  override def kind = "Timer"
}
