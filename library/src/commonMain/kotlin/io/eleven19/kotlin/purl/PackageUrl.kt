package io.eleven19.kotlin.purl

import com.ditchoom.buffer.AllocationZone
import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import kotlin.math.pow

public object PackageUrl {
    internal val PERCENT_CHAR: Char = '%'

    internal fun encodePath(path:String): String =
        path.split("/").joinToString("/") { percentEncode(it) }

    internal fun percentEncode(b: Byte): ByteArray {
        val hexChars = "0123456789ABCDEF"
        val b1 = hexChars[(b.toInt() shr 4) and 0xF].code.toByte()
        val b2 = hexChars[b.toInt() and 0xF].code.toByte()
        return byteArrayOf(PERCENT_CHAR.code.toByte(), b1, b2)
    }

    public fun percentEncode(source:String): String {
        if(source.isEmpty()) return source

        val bytes = source.encodeToByteArray()
        var off = 0
        var idx = indexOfUnsafeChar(bytes, off)

        val buffer = PlatformBuffer.allocate(
            bytes.size * 3,
            zone = AllocationZone.Direct,
            byteOrder = ByteOrder.BIG_ENDIAN
        )

        while(true){
            val len = idx - off
            if(len > 0){
                buffer.writeBytes(bytes, off, len)
                off += len
            }

            buffer.writeBytes(percentEncode(bytes[off++]))
            idx = indexOfUnsafeChar(bytes, off)

            if(idx == -1){
                val rem = bytes.size - off
                if(rem > 0){
                    buffer.writeBytes(bytes, off, rem)
                }
                break
            }
        }
        val pos = buffer.position()
        buffer.resetForRead()
        return buffer.readString(pos)
    }

    private fun indexOfUnsafeChar(bytes: ByteArray, start: Int):Int {
        for (i in start until bytes.size) {
            if (shouldEncode(bytes[i].toInt())) {
                return i
            }
        }
        return -1
    }

    private fun isUnreserved(c:Int): Boolean =
        isValidCharForKey(c) || c == '~'.code

    private fun isValidCharForKey(c: Int): Boolean =
        ASCII.isAlphaNumeric(c) || c == '.'.code || c == '_'.code || c == '-'.code

    private fun isValidCharForType(c: Int): Boolean =
        ASCII.isAlphaNumeric(c) || c == '.'.code || c == '+'.code || c == '-'.code

    private fun shouldEncode(c:Int): Boolean = !isUnreserved(c)
}