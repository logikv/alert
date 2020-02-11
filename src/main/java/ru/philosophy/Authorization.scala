package ru.philosophy

import scala.tools.nsc.Driver
import org.openqa.selenium.chrome.ChromeDriver

object Authorization {

  def authorize(driver: ChromeDriver)={
    driver.get("https://intranet.phoenixit.ru/about_us/6884/")

    driver.findElementById("login").sendKeys("nxxx")
    driver.findElementById("pass").sendKeys("xxxr")
    driver.findElementByClassName("login__btn").submit()


  }

}
