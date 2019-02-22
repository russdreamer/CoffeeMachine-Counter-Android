package com.toolittlespot.coffeemachinecleancounter.businesslogic.application

class Achievement (var name:String, var type:Type, var description: String){
    var message: String? = null

    enum class Type{
        NONE,
        SECRET_MESSAGE,
        CLEAN_HANDS,
        SOUNDS_FUNNY,
    }
}