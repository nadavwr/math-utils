val Scala_2_12 = "2.12.4"
val Scala_2_11 = "2.11.12"

lazy val mathUtils = crossProject(JVMPlatform, NativePlatform)
  .withoutSuffixFor(JVMPlatform)
  .in(file("."))
  .settings(
    scalaVersion := Scala_2_11,
    moduleName := "math-utils",
    organization := "com.github.nadavwr",
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.6.3" % "test",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    nativeLinkStubs := true,
    resolvers += Resolver.bintrayRepo("nadavwr", "maven"),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  )
  .jvmSettings(
    libraryDependencies += "com.github.jnr" % "jnr-ffi" % "2.1.6",
    crossScalaVersions := Seq(Scala_2_11, Scala_2_12)
  )
  .nativeSettings(
    libraryDependencies += "com.github.nadavwr" %%% "libffi-scala-native" % "0.5.0",
    crossScalaVersions := Seq(Scala_2_11)
  )
lazy val mathUtilsJVM = mathUtils.jvm
lazy val mathUtilsNative = mathUtils.native
