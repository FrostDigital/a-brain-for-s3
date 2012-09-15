import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "a-brain-for-s3"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.imgscalr" % "imgscalr-lib" % "4.2",
      "com.amazonaws" % "aws-java-sdk" % "1.3.14"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
