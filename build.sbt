
lazy val akkaVersion = "2.4.12"

lazy val commonSettings = Seq(
  organization := "io.spinor",
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val pingPong = (project in file(".")).
  enablePlugins(JavaAppPackaging).
  settings(commonSettings: _*).
  settings(
    name := "pingpong",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.1",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.google.guava" % "guava" % "20.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test"
    )
  )

fork in run := true
