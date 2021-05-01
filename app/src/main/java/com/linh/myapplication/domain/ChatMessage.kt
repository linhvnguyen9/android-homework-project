package com.linh.myapplication.domain

class ChatMessage {
    var id: String? = null
    var text: String? = null
    var name: String? = null

    constructor()

    constructor(text: String?, name: String?) {
        this.text = text
        this.name = name
    }

}