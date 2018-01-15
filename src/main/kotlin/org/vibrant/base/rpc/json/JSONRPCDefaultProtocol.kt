package org.vibrant.base.rpc.json

import org.vibrant.base.rpc.JSONRPCMethod
import org.vibrant.core.node.RemoteNode
import org.vibrant.example.chat.base.jsonrpc.JSONRPCResponse

class JSONRPCDefaultProtocol: JSONRPC() {


    @JSONRPCMethod
    fun connect(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return JSONRPCResponse(
                result = true,
                error = null,
                id = request.id
        )
    }
}