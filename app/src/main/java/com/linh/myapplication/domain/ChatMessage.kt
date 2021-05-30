package com.linh.myapplication.domain

class ChatMessage {
    var text: String? = null
    var name: String? = null
    var photoUrl: String? = null
    var imageUrl: String? = null

    constructor()

    constructor(text: String?, name: String?) {
        this.text = text
        this.name = name
    }

    constructor(text: String?, name: String?, photoUrl: String?, imageUrl: String?) {
        this.text = text
        this.name = name
        this.photoUrl = photoUrl
        this.imageUrl = imageUrl
    }
}