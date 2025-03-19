package io.eleven19.kotlin.purl
import io.eleven19.kotlin.purl.ASCII.indexOfFirstUpperCaseChar
import kotlin.test.Test
import kotlin.test.assertEquals

class ASCIITests {
    @Test
    fun `it should be possible to get the first uppercase character in a string`(){
        val input = "helloWorld"
        val result = input.indexOfFirstUpperCaseChar()
        assertEquals(5, result)
    }
}