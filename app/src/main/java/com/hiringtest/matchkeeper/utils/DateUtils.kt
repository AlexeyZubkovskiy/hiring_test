package com.hiringtest.matchkeeper.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val DEFAULT_US_DATE_FORMAT_PATTERN = "MM/dd/yyyy"
val DEFAULT_DATE_FORMAT = SimpleDateFormat(
	DEFAULT_US_DATE_FORMAT_PATTERN,
	Locale.getDefault()
)

fun Date.formatDefault(): String = DEFAULT_DATE_FORMAT.format(this)