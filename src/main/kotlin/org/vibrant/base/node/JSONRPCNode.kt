package org.vibrant.base.node

import org.vibrant.base.http.HTTPJsonRPCPeer
import org.vibrant.base.rpc.json.JSONRPCEntity
import org.vibrant.base.rpc.json.JSONRPCRequest
import org.vibrant.core.node.AbstractNode
import org.vibrant.core.node.RemoteNode
import org.vibrant.example.chat.base.jsonrpc.JSONRPCResponse

@Suppress("AddVarianceModifier")
abstract class JSONRPCNode<T: HTTPJsonRPCPeer>: AbstractNode<JSONRPCEntity>(){

    @Suppress("MemberVisibilityCanBePrivate")
    protected var requestID: Long = 0

    abstract val peer: T

    override fun connect(remoteNode: RemoteNode): Boolean {
        val response1 = this.request(this.createRequest("echo", arrayOf("peer")), remoteNode)
        return response1.result == "peer"
    }

    override fun request(payload: JSONRPCEntity, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return this.peer.request(remoteNode, payload as JSONRPCRequest)
    }

    override fun start() {
        this.peer.start()
    }

    override fun stop() {
        this.peer.stop()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun createRequest(method: String, params: Array<Any>): JSONRPCRequest {
        return JSONRPCRequest(method, params, this.requestID++)
    }

}