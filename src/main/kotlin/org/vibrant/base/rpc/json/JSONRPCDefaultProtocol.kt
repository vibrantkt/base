package org.vibrant.base.rpc.json

import org.vibrant.base.rpc.JSONRPCMethod
import org.vibrant.core.node.RemoteNode

@Suppress("UNUSED_PARAMETER")
class JSONRPCDefaultProtocol: JSONRPC() {


    init {
        this.handlers["connect"] = { request, _ ->
            JSONRPCResponse(
                    result = true,
                    error = null,
                    id = request.id
            )
        }
    }
}