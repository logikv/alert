package ru.philosophy

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, GregorianCalendar}
import java.util.function.Consumer

import ru.philosophy.DateFilter.overThreeDayFilter
import ru.philosophy.DateFilter.todayFilter
import ru.philosophy.Parser.accountParse
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.By
import ru.philosophy.Authorization.authorize

import scala.collection.JavaConversions._

object Main extends App{

  val MANAGER_ID = "23257"

  println(System.getenv("WEBDRIVER_PATH"))

  System.setProperty("webdriver.chrome.driver", System.getenv("WEBDRIVER_PATH"))
  val options = new ChromeOptions()
  options.addArguments("--headless")
  options.addArguments("--no-sanbox")
  options.addArguments("--disable-dev-shm-usage")

  val driver = new ChromeDriver(options)

  authorize(driver)

  val element = driver.findElementByCssSelector("a[href$=\"/user-profile/?ID="+MANAGER_ID+"\"]")

  val usersLinks = element.findElement(By.xpath("../.."))
    .findElements(By.className("userlist"))
    .map(x => x.findElements(By.tagName("a")).get(0))
    .map(x => x.getAttribute("href"))

  val people =  usersLinks.map(x => accountParse(driver, x)).filter(x => x.name != "err")

  var birthdayToday = people.filter(todayFilter)

  var birthdayOverThreeDays = people.filter(overThreeDayFilter)

  println("today : " + birthdayToday.toString())
  println("over3 : " + birthdayOverThreeDays.toString())

  var msg = ""
  if(birthdayToday.nonEmpty){
    msg += "Сегодня день рожденя у:\n"
    birthdayToday.foreach(x => msg += "\t" + x.name + "\n")
  }
  if(birthdayOverThreeDays.nonEmpty){
    msg += "Через 3 дня будет день рожденя у:\n"
    birthdayOverThreeDays.foreach(x => msg += "\t" + x.name + "\n")
  }


  val FROM = "birthdayalert01@gmail.com"
  val PASS = "xxx"
  val TO = "nartemenko@fil-it.ru"
  val SUB = "Оповещение о дне рождения."

  if(msg.nonEmpty) {
    SendMailSSL.send(FROM, PASS, TO, SUB, msg)
  }

  driver.close()

}
