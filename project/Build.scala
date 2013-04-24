import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "a-brain-for-s3"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      javaCore,
      "org.imgscalr" % "imgscalr-lib" % "4.2",
      "com.amazonaws" % "aws-java-sdk" % "1.3.14",
      "commons-lang" % "commons-lang" % "2.6",
      "com.drewnoakes" % "metadata-extractor" % "2.6.2"
      //"javax.media.jai" % "com.springsource.javax.media.jai.core" % "1.1.3"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
    )

}
