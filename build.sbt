
name := "moesif-play-filter"
organization := "com.moesif.filter"

version := "1.0"

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

url("http://www.moesif.com/")
publishMavenStyle := true
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/Moesif/moesif-play-filter"),
    "scm:git@github.com:Moesif/moesif-play-filter.git"
  )
)
organizationName := "Moesif Inc"
organizationHomepage := Some(url("http://www.moesif.com/"))
developers += Developer("moesif", "Moesif API", "support@moesif.com", url("https://www.moesif.com"))
crossPaths := false
