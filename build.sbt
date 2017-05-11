import sbtcrossproject.{crossProject, CrossType}

val Scala_2_12 = "2.12.2"
val Scala_2_11 = "2.11.11"

def crossAlias(aliasName: String, commandName: String, projectNames: String*): Command =
  BasicCommands.newAlias(aliasName, (projectNames.map { projectName =>
    s""";++$Scala_2_12
       |;${projectName}JVM/$commandName
       |;++$Scala_2_11
       |;${projectName}JVM/$commandName
       |;${projectName}Native/$commandName
     """.stripMargin
  }.mkString))

def forallAlias(aliasName: String, commandName: String, projectNames: String*): Command =
  BasicCommands.newAlias(aliasName, (projectNames.map { projectName =>
    s""";${projectName}JVM/$commandName
       |;${projectName}Native/$commandName
    """.stripMargin
  }.mkString))

lazy val commonSettings = Def.settings(
  scalaVersion := "2.11.8",
  organization := "com.github.nadavwr",
  publishArtifact in (Compile, packageDoc) := false,
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
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
    moduleName := "math-utils"
  )
  .jvmSettings(
    test := (run in Compile).toTask("").value
  )
  .nativeSettings(
    test := run.toTask("").value,
    resolvers += Resolver.bintrayRepo("nadavwr", "maven"),
    libraryDependencies += "com.github.nadavwr" %%% "libffi-scala-native" % "0.3.4"
  )
lazy val mathUtilsJVM = mathUtils.jvm
lazy val mathUtilsNative = mathUtils.native

lazy val mathUtilsRoot = (project in file("."))
  .aggregate(mathUtilsJVM, mathUtilsNative)
  .settings(
    commonSettings,
    unpublished,
    commands += crossAlias("publishLocal", "publishLocal", "mathUtils"),
    commands += crossAlias("publish", "publish", "mathUtils"),
    commands += forallAlias("clean", "clean", "mathUtils")
  )

