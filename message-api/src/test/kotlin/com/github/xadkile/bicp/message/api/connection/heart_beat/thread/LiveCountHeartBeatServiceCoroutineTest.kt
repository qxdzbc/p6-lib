package com.github.xadkile.bicp.message.api.connection.heart_beat.thread

import com.github.michaelbull.result.unwrap
import com.github.xadkile.bicp.message.api.connection.heart_beat.HeartBeatService
import com.github.xadkile.bicp.test.utils.TestOnJupyter
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.zeromq.SocketType
import org.zeromq.ZMQ

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LiveCountHeartBeatServiceThreadTest : TestOnJupyter() {
    lateinit var hbService: HeartBeatService
    lateinit var hbService2: HeartBeatService
    lateinit var socket: ZMQ.Socket
    val liveCount = 4
    val interval:Long = 500

    @BeforeEach
    fun beforeEach() {
        this.ipythonContext.startIPython()
        socket = this.zcontext.createSocket(SocketType.REQ).also {
            it.connect(this.ipythonContext.getChannelProvider().unwrap().heartbeatChannel().makeAddress())
        }
        hbService2 = LiveCountHeartBeatServiceThread(
            this.zcontext,this.ipythonContext.getSocketProvider().unwrap(), liveCount, interval,
        )
        hbService = this.ipythonContext.getHeartBeatService().unwrap()
    }

    @AfterEach
    fun afterEach(){
        hbService.stop()
        this.ipythonContext.stopIPython()
    }

    @Test
    fun dumm(){
        this.ipythonContext.startIPython()
        hbService2.start()
    }

    @Test
    fun start() {
        this.ipythonContext.startIPython()
        assertTrue(hbService.isServiceRunning())
    }

    @Test
    fun isAlive() {
        this.ipythonContext.startIPython()
        hbService.start()
        assertTrue(hbService.isServiceRunning())
        Thread.sleep(1000)
        assertTrue(hbService.isHBAlive())
    }

    @Test
    fun stop() {
        this.ipythonContext.startIPython()
        hbService.start()
        hbService.stop()
        assertFalse(hbService.isServiceRunning())
    }
}
