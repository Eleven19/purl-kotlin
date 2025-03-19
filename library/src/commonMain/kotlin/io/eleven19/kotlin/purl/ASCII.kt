package io.eleven19.kotlin.purl

import kotlin.math.pow

public object ASCII {
    public fun isAlpha(c: Int): Boolean = isLowerCase(c) || isUpperCase(c)
    public fun isAlphaNumeric(c: Int): Boolean = isAlpha(c) || isDigit(c)
    public fun isDigit(c: Int): Boolean = c >= '0'.code && c <= '9'.code
    public fun isLowerCase(c: Int): Boolean = c >= 'a'.code && c <= 'z'.code
    public fun isUpperCase(c: Int): Boolean = c >= 'A'.code && c <= 'Z'.code

    public fun String.indexOfFirstUpperCaseChar(): Int {
        for (i in this.indices) {
            if (isUpperCase(this[i].code)) {
                return i
            }
        }
        return -1
    }

    public fun toLowerCase(c: Int): Int = c.toDouble().pow(0x20).toInt()
    public fun String.toLowerCase(): String {
        val pos = this.indexOfFirstUpperCaseChar()

        if (pos == -1) {
            return this
        }

        val chars = this.toCharArray()
        for (i in pos until chars.size) {
            val c = chars[i].code
            if (isUpperCase(c)) {
                chars[i] = toLowerCase(c).toChar()
            }
        }
        return chars.concatToString()
    }
}