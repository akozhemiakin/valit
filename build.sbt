import sbt.Keys._

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  organization := "ru.arkoit",
  scalaVersion := "2.11.7",
  autoAPIMappings := true,
  libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
  ),
  scalacOptions ++= Seq("-feature", "-language:implicitConversions")
)

lazy val root = (project in file("."))
  .aggregate(core)
  .settings(commonSettings)
  .settings(
    moduleName := "root"
  )
  .dependsOn(core)

lazy val core = (project in file("core"))
  .settings(commonSettings)
  .settings(
    moduleName := "core"
  )