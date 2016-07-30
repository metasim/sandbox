enablePlugins(ScalaJSPlugin)

name := "Sandbox Root"

scalaVersion in ThisBuild := "2.11.8"


lazy val root = project.in(file(".")).
  aggregate(js, jvm).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val sandbox = crossProject.in(file(".")).
  settings(
    name := "Sandbox",
    version := "0.1-SNAPSHOT",
    libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.0",
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.4.3" % "test",
    testFrameworks += new TestFramework("utest.runner.Framework")
  ).
  jvmSettings(
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided"
  ).
  jsSettings(
    // Add JS-specific settings here
  )

lazy val jvm = sandbox.jvm
lazy val js = sandbox.js
