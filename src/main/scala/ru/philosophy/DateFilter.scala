package ru.philosophy

import java.util.{Calendar, GregorianCalendar}

object DateFilter {

  var OVER_N_DAYS = 3

  def todayFilter(p: People): Boolean = {
    val day = p.birthday.split('.').head.toInt
    val month = p.birthday.split('.').last.toInt - 1

    val curDate = new GregorianCalendar()

    curDate.get(Calendar.DAY_OF_MONTH) == day && curDate.get(Calendar.MONTH) == month
  }

  def overThreeDayFilter(p: People): Boolean = {
    val day = p.birthday.split('.').head.toInt
    val month = p.birthday.split('.').last.toInt - 1

    val curDate = new GregorianCalendar()

    curDate.add(Calendar.DAY_OF_MONTH, 3)

    curDate.get(Calendar.DAY_OF_MONTH) == day && curDate.get(Calendar.MONTH) == month
  }

}
