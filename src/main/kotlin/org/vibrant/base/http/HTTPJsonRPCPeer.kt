package org.vibrant.base.http

import org.vibrant.base.rpc.json.JSONRPCRequest
import org.vibrant.base.rpc.json.JSONRPCSerializer
import org.vibrant.core.node.RemoteNode
import org.vibrant.base.rpc.WrongProtocolException
import org.vibrant.base.rpc.json.JSONRPC
import org.vibrant.example.chat.base.jsonrpc.JSONRPCResponse

open class HTTPJsonRPCPeer(port: Int, private val rpc: JSONRPC): HTTPPeer(port, object: HTTPPeerConfig(endpoint = "/rpc"){}) {

    protected var requestID: Long = 0

    override fun connect(remoteNode: RemoteNode) {
        val request = JSONRPCRequest("echo", arrayOf("peer"), this.requestID++)

    }


    override fun handleRequest(request: Request): ByteArray {
        val remotePort = request.headers["peer-port"]?.toInt()
        if(remotePort != null){
            val remoteNode = RemoteNode(request.request.remoteAddr, remotePort)
            val jsonRPCRequest = JSONRPCSerializer.deserialize(request.body) as JSONRPCRequest
            val response = rpc.invoke(jsonRPCRequest, remoteNode)
            return JSONRPCSerializer.serialize(response)
        }else {
            throw WrongProtocolException("Expected 'peer-port' header")
        }
    }

}