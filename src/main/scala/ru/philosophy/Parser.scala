package ru.philosophy

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, GregorianCalendar}
import java.util.function.Consumer

import org.openqa.selenium.By
import org.openqa.selenium.remote.RemoteWebDriver


object Parser {


  val LOGIN = "xxxx"
  val PASSWORD = "xxxx"

  def accountParse(link: String)(implicit driver: RemoteWebDriver): People = {

    driver.get(link)

    if (driver.getCurrentUrl == "https://sso.phoenixit.ru/login") {
      driver.findElementById("email").sendKeys(LOGIN)
      driver.findElementById("password").sendKeys(PASSWORD)
      driver.findElementByClassName("login__btn").submit()
    }

    Thread.sleep(2000)

    try {

      val name = driver.findElementByClassName("personal-data").findElement(By.tagName("h1")).getText

      val birthday = driver.findElementByCssSelector("time[class$=\"indent_reset\"]").getAttribute("datetime")

      val email = "none" //driver.findElementByXPath("//*[@id=\"root\"]/main/div[2]/div/div/div[2]/div/div/div[4]/div[1]/section[1]/div[2]/article[2]/ul/li[1]")
      //.findElements(By.tagName("a")).get(0).getText

      println(name)
      People(name, birthday, email)
    } catch {
      case _: Throwable => {
        println(link + " error")
        People("err", "00.00", "err")
      }
    }
  }
}
