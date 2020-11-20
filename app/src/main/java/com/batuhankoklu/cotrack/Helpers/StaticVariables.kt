package com.batuhankoklu.cotrack.Helpers

import com.batuhankoklu.cotrack.Models.UserModel

class StaticVariables {

    companion object{

        var baseUrl : String? = "https://api.coingecko.com/api/v3/"
        var masterPageSelection : Int? = null
        var isEnteredBefore = 0

        var unitTypes = arrayListOf("₿","₺","$")
        var selectedUnit = 2

        var coinId = ""

        var userModel : UserModel? = null
    }
}