package org.bitbucket.xadkile.isp.ide.jupyter.message.api.session

import java.util.*

/**
 * A connection session.
 * A session is unique and one-time use.
 * When the kernel restart, the old session must be closed and a new session must be created.
 * A closed session cannot be reused
 */
class SessionInfo(
    sessionId: String,
    username: String,
    key: String
) : AutoCloseable{
    val sessionId: String = sessionId
        get() {
            return getPropWithCheck(field, "sessionId = [${field}]")
        }

    val username: String = username
        get() {
            return getPropWithCheck(field, "username = [${field}]")
        }
    val key: String = key
        get() {
            return getPropWithCheck(field, "key = [${field}]")
        }

    private var _isOpen: Boolean = true

    companion object {
        private fun ExceptionMessage(info: String) = "Session is closed, can't get property: $info"

        /**
         * [sessionId] autogenerated
         * [username] fetched from system
         */
        fun autoCreate(key: String): SessionInfo {
            return SessionInfo(
                sessionId = UUID.randomUUID().toString(),
                username = System.getProperty("user.name"),
                key = key
            )
        }
    }
    /**
     * see if this Session is fit for use
     */
    @Throws(IllegalStateException::class)
    fun checkLegal(errorMessage:String=""){
        if(this.isClosed()){
            throw IllegalStateException("Session is closed.\n$errorMessage")
        }
    }

    fun getKeyByte():ByteArray{
        return this.key.toByteArray(Charsets.UTF_8)
    }

    fun isClosed():Boolean{
        return !this.isOpen()
    }
    fun isOpen(): Boolean {
        return _isOpen
    }

    override fun close() {
        this._isOpen = false
    }

    private fun <T> getPropWithCheck(propValue: T, propName: String): T {
        if (this.isOpen()) {
            return propValue
        } else {
            throw IllegalStateException(ExceptionMessage(propName))
        }
    }
}