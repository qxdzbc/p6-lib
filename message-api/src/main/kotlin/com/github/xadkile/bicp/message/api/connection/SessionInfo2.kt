package com.github.xadkile.bicp.message.api.connection

import java.util.*

/**
 * Provide user name, encryption key, and session id. These are for making message
 */
data class SessionInfo2 constructor(
    private val sessionId: String,
    private val username: String,
    private val key: String
) : Session{
    companion object {
        /**
         * [sessionId] autogenerated
         * [username] fetched from system
         */
        fun autoCreate(key: String): SessionInfo2 {
            return SessionInfo2(
                sessionId = UUID.randomUUID().toString(),
                username = System.getProperty("user.name"),
                key = key
            )
        }
    }

    override fun getUserName(): String {
        return this.username
    }

    override fun getKey(): String {
        return this.key
    }

    override fun getSessionId(): String {
        return this.sessionId
    }
}