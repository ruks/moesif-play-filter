// SBT 1.11 natively suppoerts the new Maven Central on Sonatype
// It reads from the environment variables SONATYPE_USERNAME and SONATYPE_PASSWORD
// https://eed3si9n.com/sbt-1.11.0
// This config below is only for publishing to the snapshot repository when a -SNAPSHOT version is used
ThisBuild / publishTo := {
  val centralSnapshots = "https://central.sonatype.com/repository/maven-snapshots/"
  if (isSnapshot.value) Some("central-snapshots" at centralSnapshots)
  else localStaging.value
}