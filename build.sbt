name := "alert"

version := "0.2"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.joda/joda-convert
libraryDependencies += "org.joda" % "joda-convert" % "2.2.1"
// https://mvnrepository.com/artifact/joda-time/joda-time
libraryDependencies += "joda-time" % "joda-time" % "2.10.5"
// https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "3.141.59"


// https://mvnrepository.com/artifact/com.sun.mail/javax.mail
libraryDependencies += "com.sun.mail" % "javax.mail" % "1.6.2"


// https://mvnrepository.com/artifact/com.codeborne/selenide
libraryDependencies += "com.codeborne" % "selenide" % "5.7.0"


lazy val commonSettings = Seq(
  version := "0.2",
  organization := "ru.philosophy",
  scalaVersion := "2.11.8",
  test in assembly := {}
)

lazy val app = (project in file("app")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("ru.philosophy.Main"),
  )

lazy val utils = (project in file("utils")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "utils.jar",
  )