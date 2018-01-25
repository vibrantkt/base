package org.vibrant.base.rpc.json

import org.vibrant.base.node.JSONRPCNode
import org.vibrant.base.rpc.JSONRPCMethod
import org.vibrant.core.node.RemoteNode

interface JSONRPCChainSynchronization<T: JSONRPCNode<*>> {
    val node: T


    private fun handleBlock(){

    }


    @JSONRPCMethod
    fun onNewBlock(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return JSONRPCResponse(true, null, request.id)
    }

}