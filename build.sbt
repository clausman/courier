name := "courier"

version := "1.0-SNAPSHOT"

// Assuming all dependencies are locally cached, this will stop it from looking for new versions
offline := true

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.google.guava" % "guava" % "14.0",
  "com.google.inject" % "guice" % "3.0",
  "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13",
  "org.antlr" % "stringtemplate" % "4.0.2"
)     

play.Project.playJavaSettings
