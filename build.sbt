import AssemblyKeys._
import complete.DefaultParsers._

lazy val commonSettings = Seq(
  version := "0.1.0",
  organization := "org.next",
  scalaVersion := "2.10.4",
  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7", "-Xlint")

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(assemblySettings: _*)
  .settings(name := "spark-machine")

resolvers += Resolver.sonatypeRepo("public")
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/" 
resolvers += Resolver.mavenLocal

// 手动下载jar包存储
unmanagedBase := baseDirectory.value / "custom_lib"

// additional libraries
libraryDependencies ++= Seq(

  "com.typesafe" % "config" % "1.2.1",
  "org.json4s" %% "json4s-native" % "3.3.0",

  "org.apache.hadoop" % "hadoop-client" % "2.6.0" excludeAll ExclusionRule(organization = "javax.servlet"),
  "org.apache.hadoop" % "hadoop-yarn-client" % "2.6.0",

  "org.apache.spark" % "spark-core_2.10" % "1.5.0"  % "provided" exclude("com.google.guava", "guava"),
  "org.apache.spark" % "spark-yarn_2.10" % "1.5.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.5.0",

  "com.twitter" % "parquet-hadoop" % "1.5.0",
  "com.twitter" % "parquet-hadoop-bundle" % "1.5.0",

  "com.databricks" %% "spark-csv" % "1.4.0",

  "org.scalaj" % "scalaj-http_2.10" % "2.3.0"
)

mergeStrategy in assembly <<= (mergeStrategy in assembly) {
  (old) => {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }
}

