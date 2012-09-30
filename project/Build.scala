import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "a-brain-for-s3"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.imgscalr" % "imgscalr-lib" % "4.2",
      "com.amazonaws" % "aws-java-sdk" % "1.3.14",
      "commons-lang" % "commons-lang" % "2.6"
      //"javax.media.jai" % "com.springsource.javax.media.jai.core" % "1.1.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    )

}
