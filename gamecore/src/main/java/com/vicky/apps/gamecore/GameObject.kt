package com.vicky.apps.gamecore

class GameObject (val item:String){
    var attacks:List<GameObject> = arrayListOf()
    var defends:List<GameObject> = arrayListOf()
}