import Dependencies._

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"


lazy val root = (project in file("."))
  .settings(
    name := "freemonad-sample1",
  )
  .aggregate(core, model, interpreter)

lazy val core = (project in file("core"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-free" % "2.4.2",
    libraryDependencies += scalaTest % Test
  )

lazy val model = (project in file("model"))
  .settings(
    libraryDependencies += scalaTest % Test
  )
  .dependsOn(core)

lazy val interpreter = (project in file("interpreter"))
  .settings(
    libraryDependencies += scalaTest % Test
  )
  .dependsOn(model)
