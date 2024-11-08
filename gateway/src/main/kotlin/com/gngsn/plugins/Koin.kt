package com.gngsn.plugins

import com.gngsn.application.port.SaveConnectionInput
import com.gngsn.application.usecase.SaveConnectionUseCase
import io.ktor.server.application.*
import org.koin.core.logger.Level
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val appModule = module {
    singleOf(::SaveConnectionUseCase) { bind<SaveConnectionInput>() }
}

fun Application.configureKoin() {
    install(Koin) {
        printLogger(Level.DEBUG)
        modules(appModule)
    }
}