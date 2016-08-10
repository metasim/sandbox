name := "ImageJ Processing"

scalaVersion := "2.11.7"

resolvers += "imagej.public" at "http://maven.imagej.net/content/groups/public"

libraryDependencies ++= Seq(
    //"net.imagej" % "imagej" % "2.0.0-rc-50",
    "net.imagej" % "imagej-ops" % "0.31.0",
    "org.scijava" %  "scripting-jython" % "0.3.0"
)
