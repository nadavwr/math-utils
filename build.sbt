import sbtcrossproject.{crossProject, CrossType}

val Scala_2_12 = "2.12.2"
val Scala_2_11 = "2.11.11"

def crossAlias(aliasName: String, commandName: String, projectNames: String*): Command =
  BasicCommands.newAlias(aliasName, projectNames.map { projectName =>
    s""";++$Scala_2_12
       |;${projectName}JVM/$commandName
       |;++$Scala_2_11
       |;${projectName}JVM/$commandName
       |;${projectName}Native/$commandName
     """.stripMargin
  }.mkString)

def forallAlias(aliasName: String, commandName: String, projectNames: String*): Command =
  BasicCommands.newAlias(aliasName, projectNames.map { projectName =>
    s""";${projectName}JVM/$commandName
       |;${projectName}Native/$commandName
    """.stripMargin
  }.mkString)

lazy val commonSettings = Def.settings(
  scalaVersion := Scala_2_11,
  organization := "com.github.nadavwr",
  publishArtifact in (Compile, packageDoc) := false,
  resolvers += Resolver.bintrayRepo("nadavwr", "maven"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  scalacOptions += "-feature"
)

lazy val unpublished = Def.settings(
  publish := {},
  publishLocal := {},
  publishM2 := {}
)

lazy val mathUtils = crossProject(JVMPlatform, NativePlatform)
  .in(file("."))
  .settings(
    commonSettings,
    moduleName := "math-utils",
    test := (run in Compile).toTask("").value
  )
  .jvmSettings(
    libraryDependencies += "com.github.jnr" % "jnr-ffi" % "2.1.6"
  )
  .nativeSettings(
    libraryDependencies += "com.github.nadavwr" %%% "libffi-scala-native" % "0.4.0-SNAPSHOT"
  )
lazy val mathUtilsJVM = mathUtils.jvm
lazy val mathUtilsNative = mathUtils.native

lazy val mathUtilsTest =
  crossProject(JVMPlatform, NativePlatform)
    .crossType(CrossType.Full)
    .in(file("mathUtilsTest"))
    .settings(
      commonSettings,
      unpublished,
      moduleName := "math-utils-test",
      libraryDependencies += "com.github.nadavwr" %%% "makeshift" % "0.2.0-SNAPSHOT",
      test := { (run in Compile).toTask("").value }
    )
    .dependsOn(mathUtils)
lazy val mathUtilsTestJVM = mathUtilsTest.jvm
lazy val mathUtilsTestNative = mathUtilsTest.native

lazy val mathUtilsRoot =
  Project("mathUtils", file("."))
    .aggregate(mathUtilsJVM, mathUtilsNative)
    .settings(
      commonSettings,
      unpublished,
      commands += crossAlias("crossPublishLocal", "publishLocal", "mathUtils"),
      commands += crossAlias("crossPublish", "publish", "mathUtils"),
      commands += crossAlias("crossTest", "test", "mathUtilsTest"),
      commands += forallAlias("test", "test", "mathUtilsTest"),
      commands += forallAlias("clean", "clean", "mathUtils", "mathUtilsTest")
    )

