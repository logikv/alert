package ru.philosophy

import org.openqa.selenium.remote.RemoteWebDriver

object Authorization {

  def authorize(implicit driver: RemoteWebDriver) = {

    println("prepare to login")

    driver.get("https://intranet.phoenixit.ru/about_us/6884/")

    driver.findElementById("login").sendKeys("nxxx")
    driver.findElementById("pass").sendKeys("xxxr")
    driver.findElementByClassName("login__btn").submit()

    println("login complete")
  }

}
