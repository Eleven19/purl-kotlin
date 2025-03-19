package io.eleven19.kotlin.purl

import io.eleven19.kotlin.purl.PackageUrl.encodePath
import io.eleven19.kotlin.purl.PackageUrl.percentEncode

public data class Purl(
    val type:String,
    val namespace:String?,
    val name:String,
    val version:String?,
    val qualifiers:Map<String, String>?,
    val subpath:String?,
){

    public fun canonicalize(coordinatesOnly: Boolean = false):String{
        val purl = StringBuilder()
        purl.append(SCHEME_PART)
        if(namespace != null){
            purl.append(encodePath(namespace))
            purl.append("/")
        }
        purl.append(percentEncode(name))
        if(version != null){
            purl.append("@").append(percentEncode(version))
        }


        return purl.toString()
    }

    public companion object {
        public const val SCHEME: String = "pkg"
        public const val SCHEME_PART: String = "$SCHEME:"
    }
}