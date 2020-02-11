package ru.philosophy

import java.net.URL

import ru.philosophy.DateFilter.overThreeDayFilter
import ru.philosophy.DateFilter.todayFilter
import ru.philosophy.Parser.accountParse
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.By
import org.openqa.selenium.remote.RemoteWebDriver
import ru.philosophy.Authorization.authorize

import scala.collection.JavaConversions._

object Main extends App {

  val MANAGER_ID = "23257"

  var webDriver_path: String = "target/chromedriver74"

  //  if (sys.env("WEBDRIVER_PATH") != null) {
  //    println(sys.env("WEBDRIVER_PATH"))
  //    webDriver_path = sys.env("WEBDRIVER_PATH")
  //    println("Read from env")
  //  }
  println("run")

  System.setProperty("webdriver.chrome.driver", webDriver_path)
  val options = new ChromeOptions()
    .setBinary(webDriver_path)
    .setHeadless(false)
    .addArguments("--no-sanbox")
    .addArguments("--disable-gpu")
    .addArguments("--log-level=ALL")
    .addArguments("--disable-dev-shm-usage")

  println("prepare to start driver")

  import org.openqa.selenium.remote.DesiredCapabilities

  val cap = DesiredCapabilities.chrome
  implicit val driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap)

  authorize

  val element = driver.findElementByCssSelector("a[href$=\"/user-profile/?ID=" + MANAGER_ID + "\"]")

  val usersLinks = element.findElement(By.xpath("../.."))
    .findElements(By.className("userlist"))
    .map(x => x.findElements(By.tagName("a")).get(0))
    .map(x => x.getAttribute("href"))

  val people = usersLinks
    .map(x => accountParse(x))
    .filter(x => x.name != "err")

  var birthdayToday = people.filter(todayFilter)

  var birthdayOverThreeDays = people.filter(overThreeDayFilter)

  println("today : " + birthdayToday.toString())
  println("over3 : " + birthdayOverThreeDays.toString())

  var msg = ""
  if (birthdayToday.nonEmpty) {
    msg += "Сегодня день рожденя у:\n"
    birthdayToday.foreach(x => msg += "\t" + x.name + "\n")
  }
  if (birthdayOverThreeDays.nonEmpty) {
    msg += "Через 3 дня будет день рожденя у:\n"
    birthdayOverThreeDays.foreach(x => msg += "\t" + x.name + "\n")
  }


  val FROM = "birthdayalert01@gmail.com"
  val PASS = "xxx"
  val TO = "nartemenko@fil-it.ru"
  val SUB = "Оповещение о дне рождения."

  if (msg.nonEmpty) {
    SendMailSSL.send(FROM, PASS, TO, SUB, msg)
  }

  driver.close()

}
