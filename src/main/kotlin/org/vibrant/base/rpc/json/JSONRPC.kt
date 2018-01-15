@file:Suppress("UNUSED_PARAMETER")

package org.vibrant.base.rpc.json

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import mu.KotlinLogging
import org.vibrant.base.rpc.RPC
import org.vibrant.core.node.RemoteNode
import org.vibrant.example.chat.base.jsonrpc.JSONRPCResponse

abstract class JSONRPC: RPC() {

    protected val logger = KotlinLogging.logger{}

    fun invoke(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return this::class.java.getMethod(request.method, JSONRPCRequest::class.java, RemoteNode::class.java).invoke(this, request, remoteNode) as JSONRPCResponse<*>
    }
}