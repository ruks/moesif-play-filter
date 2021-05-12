lazy val repoCredsRealm = sys.env.get("REPO_CREDS_REALM").getOrElse("Please set env var: REPO_CREDS_REALM")
lazy val repoCredsHost = sys.env.get("REPO_CREDS_HOST").getOrElse("Please set env var: REPO_CREDS_HOST")
lazy val repoCredsUser = sys.env.get("REPO_CREDS_USER").getOrElse("Please set env var: REPO_CREDS_USER")
lazy val repoCredsPassword = sys.env.get("REPO_CREDS_PASSWORD").getOrElse("Please set env var: REPO_CREDS_PASSWORD")

lazy val repoFriendlyName = sys.env.get("REPO_PUBLISH_FRIENDLY_NAME").getOrElse("Default publish friendly name")
lazy val repoUrl = sys.env.get("REPO_PUBLISH_URL").getOrElse("Please set env var: REPO_PUBLISH_URL")

// If we prefer to get credentials from a local file,
//lazy val repoCredsFile = "myRepo.credentials"
//credentials += Credentials(Path.userHome / ".sbt" / repoCredsFile)

sonatypeProfileName := sys.env.get("SONATYPE_PROFILE_NAME").getOrElse("Please set env var: SONATYPE_PROFILE_NAME")
credentials += Credentials(repoCredsRealm, repoCredsHost, repoCredsUser, repoCredsPassword)

if (repoCredsHost.toLowerCase().contains("oss.sonatype.org".toLowerCase())) {
    publishTo := sonatypePublishToBundle.value
}
else {
    resolvers += repoFriendlyName at repoUrl
    publishTo := Some(repoFriendlyName at repoUrl)
}