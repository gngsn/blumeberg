package com.gngsn.application.usecase

import com.gngsn.application.port.SaveConnectionInput
import io.ktor.util.logging.*

internal val LOGGER = KtorSimpleLogger("com.gngsn.RequestTracePlugin")


class SaveConnectionUseCase : SaveConnectionInput {

    override fun save(connectionId: String, code: String) {
        LOGGER.info("SAVE - connectionId: ${connectionId}, code: ${code}")
//        redisCacheProvider.saveCache(connectionId, code)
    }
}
