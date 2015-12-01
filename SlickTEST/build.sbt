name := "SlickTEST"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= {


  Seq(
    "net.sourceforge.jtds"      %     "jtds" % "1.3.1",
    "com.typesafe.slick"        %%    "slick"               %   "3.0.0",
    "com.typesafe.slick"        %%    "slick-extensions"    %   "3.0.0",
    "org.scala-lang.modules"    %     "scala-async_2.11"    %   "0.9.6-RC2"
  )
}
