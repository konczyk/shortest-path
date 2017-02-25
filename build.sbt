name := "shortest-path"
scalaVersion := "2.12.1"
scalaSource in Compile := baseDirectory.value / "src"
scalaSource in Test := baseDirectory.value / "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
