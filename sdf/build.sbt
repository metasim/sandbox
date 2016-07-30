name := "Scala Data Frames"

scalaVersion := "2.11.8"

classpathTypes += "maven-plugin"

libraryDependencies ++= Seq(
  "org.nd4j" %% "nd4s" % "0.4.0",
  "org.nd4j" % "nd4j-native-platform" % "0.4.0",
  "com.nrinaudo" %% "kantan.csv" % "0.1.12",
  "com.lihaoyi" %% "pprint" % "0.4.1",
  "com.lihaoyi" %% "utest" % "0.4.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
)

testFrameworks += TestFrameworks.ScalaCheck

testFrameworks += new TestFramework("utest.runner.Framework")

