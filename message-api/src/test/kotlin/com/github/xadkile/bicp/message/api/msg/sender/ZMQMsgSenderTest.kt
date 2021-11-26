package com.github.xadkile.bicp.message.api.msg.sender

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.unwrap
import com.github.xadkile.bicp.test.utils.TestOnJupyter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.zeromq.SocketType
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ZMQMsgSenderTest : TestOnJupyter() {

    @Test
    fun send_Ok() {
        this.kernelContext.startKernel()
        // send ok
        val t = System.currentTimeMillis()
        val o = ZMQMsgSender.send(
            message = listOf("a").map { it.toByteArray() },
            socket= this.zcontext.createSocket(SocketType.REQ).also {
                it.connect(this.iPythonContextConv.getHeartBeatAddress().unwrap())
            },
            hbs= this.kernelContext.getHeartBeatService().unwrap().conv(),
            interval = 1000,
            zContext = this.kernelContext.zContext()
        )
        println(System.currentTimeMillis()-t)
        assertTrue (o is Ok,o.toString())
    }
}
