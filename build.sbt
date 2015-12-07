import sbt.Keys._

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  organization := "ru.arkoit",
  scalaVersion := "2.11.7",
  autoAPIMappings := true,
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  ),
  scalacOptions ++= Seq("-feature", "-language:implicitConversions"),
  crossScalaVersions := Seq("2.11.7", "2.10.6")
)

lazy val root = (project in file("."))
  .aggregate(core)
  .settings(commonSettings)
  .settings(noPublish)
  .settings(
    moduleName := "root"
  )
  .dependsOn(core)

lazy val core = (project in file("core"))
  .settings(commonSettings)
  .settings(
    moduleName := "core"
  )