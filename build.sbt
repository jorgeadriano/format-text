ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "format-text"
  )

libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.13.3" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.17.0" % Test
