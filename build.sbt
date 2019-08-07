
name := "moesif-play-filter"

version := "1.0"

scalaVersion := "2.11.8"

assemblyJarName in assembly := "moesif-play-filter-1.0.jar"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

// https://mvnrepository.com/artifact/com.moesif.api/moesifapi
libraryDependencies += "com.moesif.api" % "moesifapi" % "1.6.3"

// https://mvnrepository.com/artifact/com.typesafe.play/play
libraryDependencies += "com.typesafe.play" %% "play" % "2.7.3"


assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filterNot  { f =>
    f.data.getName.contains("moesifapi")
  }
}