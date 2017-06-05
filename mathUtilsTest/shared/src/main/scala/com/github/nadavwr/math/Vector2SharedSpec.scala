package com.github.nadavwr.math

import com.github.nadavwr.makeshift._

//noinspection TypeAnnotation
trait Vector2SharedSpec extends Spec with Shims {

  test("create and print vector") runWith new Fixture {
    val v = Vector2(1, 1)
    println(v)
  }

  test("peer roundtrip") runWith new Fixture {
    val v1 = Vector2(1, 1)
    val p = v1.peer
    val v2 = Vector2.fromPeer(p)
    assertThat(v1 == v2, "v1 ?= v2")
  }

}
