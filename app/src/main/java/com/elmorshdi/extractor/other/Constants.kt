package com.elmorshdi.extractor.other

object Constants {
    const val SHARED_PREFERENCES_NAME="extractor"
    const val HOUR_KEY = "hour"
    const val MINUTE_KEY = "minute"
    const val DAY_KEY = "day"
    const val MONTH_KEY = "month"
    const val YEAR_KEY = "year"
    const val DATABASE_NAME="extractor_db"
    const val ALARM_ACTION="com.tester.alarming"
    const val BOOT_COMPLETED_ACTION="android.intent.action.BOOT_COMPLETED"
    const val EMAILREGEX ="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    val PHONEREGEX ="(01)[0-9]{9}"

}