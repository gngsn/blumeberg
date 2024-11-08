package com.gngsn

import com.gngsn.application.port.SaveConnectionInput
import com.gngsn.plugins.configureHTTP
import com.gngsn.plugins.configureKoin
import com.gngsn.plugins.configureSockets
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val saveConnectionUseCase by inject<SaveConnectionInput>()

    configureKoin()
    configureSockets(saveConnectionUseCase)
    configureHTTP()
}
