package com.github.xadkile.bicp.message.api.msg.listener.exception

import com.github.xadkile.bicp.message.api.msg.protocol.data_interface_definition.IOPub

class ExecutionErrException(val content: IOPub.ExecuteError.Content) : Exception(content.toString()) {
}