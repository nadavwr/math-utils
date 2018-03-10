package com.github.nadavwr.math

import utest._

trait Vector2SharedTests extends TestSuite with Shims {

  val tests: Tests = Tests {
    "doesn't crash" - {
      Vector2(1, 1)
    }
    "peer roundtrip" - {
      val v1 = Vector2(1, 1)
      val p = v1.peer
      val v2 = Vector2.fromPeer(p)
      assert(v1 == v2)
    }
  }
}
