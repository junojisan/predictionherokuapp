name := """predictionherokuapp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "commons-logging" % "commons-logging" % "1.1.1",
  "com.google.api-client" % "google-api-client" % "1.22.0",
  "com.google.api-client" % "google-api-client-gson" % "1.22.0",
  "com.google.api-client" % "google-api-client-jackson2" % "1.22.0",
  "com.google.apis" % "google-api-services-prediction" % "v1.6-rev63-1.22.0",
  "com.google.apis" % "google-api-services-storage" % "v1-rev72-1.22.0",
  "com.google.http-client" % "google-http-client" % "1.22.0",
  "com.google.http-client" % "google-http-client-gson" % "1.22.0",
  "com.google.http-client" % "google-http-client-jackson2" % "1.22.0",
  "com.google.http-client" % "google-http-client-jdo" % "1.22.0",
  "com.google.oauth-client" % "google-oauth-client" % "1.22.0",
  "com.google.code.gson" % "gson" % "2.1",
  "org.apache.httpcomponents" % "httpclient" % "4.0.1",
  "org.apache.httpcomponents" % "httpcore" % "4.0.1",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.3",
  "javax.jdo" % "jdo2-api" % "2.3-eb",
  "javax.transaction" % "transaction-api" % "1.1-rev-1"
)


fork in run := true
