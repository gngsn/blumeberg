package com.gngsn.plugins

import com.gngsn.application.port.SaveConnectionInput
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlin.math.floor
import kotlin.time.Duration.Companion.seconds


fun s4(): String = floor((1 + Math.random()) * 0x10000).toString().substring(1)

fun getUniqueID(): String = s4() + s4() + '-' + s4()

fun Application.configureSockets(saveConnectionInput: SaveConnectionInput) {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/markets/stocks/{code}") {
            val code = call.parameters["code"] ?: ""

            val connectionId = onConnect(saveConnectionInput, code)
            var received = ""

            try {
                for (frame in incoming) {
                    val text = (frame as Frame.Text).readText()
                    println("onMessage")
                    received += text
                    send("connectionId: ${connectionId}, code: ${code}, ${text}")
                    outgoing.send(Frame.Text(text))
                }

            } catch (e: ClosedReceiveChannelException) {
                println("onClose ${closeReason.await()}")
            } catch (e: Throwable) {
                println("onError ${closeReason.await()}")
                e.printStackTrace()
            }
        }
    }
}

private fun onConnect(saveConnectionInput: SaveConnectionInput, code: String): String {
    println("onConnect")
    val connectionId = getUniqueID()
    saveConnectionInput.save(connectionId, code)
    return connectionId
}

