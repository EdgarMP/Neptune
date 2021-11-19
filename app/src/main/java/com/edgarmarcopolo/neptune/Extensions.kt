package com.edgarmarcopolo.neptune

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val ZERO = 0
private const val ZERO_DOUBLE = 0.0

fun Double?.orEmpty(): Double = this ?: ZERO_DOUBLE

fun Int?.orEmpty(): Int = this ?: ZERO

fun Float?.orEmpty(): Float = this ?: ZERO.toFloat()

fun Long?.orEmpty(): Long = this ?: ZERO.toLong()

fun Long?.isEmpty(): Boolean = (this.orEmpty() > ZERO)

fun Int?.isEmpty(): Boolean = (this.orEmpty() > ZERO)

fun Float?.isEmpty(): Boolean = (this.orEmpty() > ZERO)

fun Boolean?.orFalse(): Boolean = this ?: false

fun Boolean?.orTrue(): Boolean = this ?: true

inline fun <reified T> ArrayList<T>?.orEmpty(): ArrayList<T> = this ?: arrayListOf()

inline fun <reified T> T?.orDefault(def: T): T = this ?: def

fun Double.getCashFormat(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(this)
}