package com.batuhankoklu.cotrack.Extensions

fun String?.TwoCharAfterDot() : String{
    if(this != null){
        return (Math.floor(this.toDouble() * 100) / 100).toString()
    }
    return ""
}

fun Double?.TwoCharAfterDot() : Double{
    if(this != null){
        var value = (Math.floor(this * 100) / 100)
        return value
    }
    return 0.0
}