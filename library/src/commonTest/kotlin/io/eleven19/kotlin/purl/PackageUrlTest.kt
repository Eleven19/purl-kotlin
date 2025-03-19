package io.eleven19.kotlin.purl

import io.eleven19.kotlin.purl.PackageUrl
import kotlin.test.Test
import kotlin.test.assertEquals

class PackageUrlTest {
    @Test
    fun `test percentEncoding of byte`() {
        val encoded = PackageUrl.percentEncode(0x20.toByte())
        val encodedStr = encoded.joinToString("") { it.toInt().toChar().toString() }
        assertEquals("%20", encodedStr)
    }

    @Test
    fun `test percentEncoding of string`() {
        val encoded = PackageUrl.percentEncode("hello & goodbye, world")
        assertEquals("hello%20%26%20goodbye%2C%20world", encoded)
    }
}