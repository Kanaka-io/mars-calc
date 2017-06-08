import scala.Console

name := "mars-calc"

// in ThisBuild : for each axis in this project

scalaVersion in ThisBuild := "2.11.11"

libraryDependencies ++= Seq(
 "org.scalatest" %% "scalatest" % "3.0.1" % "test",
 "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

val dtf = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")


shellPrompt := { (s: State) => 
  "[" + Console.GREEN +  name.value  + Console.RESET + "] : " + 
    Console.CYAN + dtf.format(java.time.LocalDateTime.now) + Console.RESET + " > "
}
