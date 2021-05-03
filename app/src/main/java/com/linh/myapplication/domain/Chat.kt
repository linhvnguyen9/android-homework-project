package com.linh.myapplication.domain

class Chat {
    var studentName: String? = null
    var studentAvatarUrl: String? = null
    var studentUid: String? = null
    var lastMessage: String? = null

    constructor()

    constructor(studentName: String?, studentAvatarUrl: String?, lastMessage: String?, studentUid: String?) {
        this.studentName = studentName
        this.studentAvatarUrl = studentAvatarUrl
        this.lastMessage = lastMessage
        this.studentUid = studentUid
    }
}