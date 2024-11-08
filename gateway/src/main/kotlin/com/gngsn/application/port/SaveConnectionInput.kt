package com.gngsn.application.port

interface SaveConnectionInput {
    fun save(connectionId: String, code: String)
}