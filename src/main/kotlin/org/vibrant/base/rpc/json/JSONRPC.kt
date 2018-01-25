@file:Suppress("UNUSED_PARAMETER")

package org.vibrant.base.rpc.json

import mu.KotlinLogging
import org.vibrant.base.rpc.RPC
import org.vibrant.core.node.RemoteNode

abstract class JSONRPC: RPC() {

    protected val handlers = hashMapOf<String, (JSONRPCRequest, RemoteNode) -> JSONRPCResponse<*>>()
    protected val plugins = arrayListOf<JSONRPCPlugin>()

    fun invoke(request: JSONRPCRequest, remoteNode: RemoteNode): JSONRPCResponse<*> {
        return try {
            if(this.handlers.containsKey(request.method)){
                this.handlers[request.method]!!.invoke(request, remoteNode)
            }else{
                this.plugins.first { it.handlers.containsKey(request.method) }.handlers[request.method]!!.invoke(request, remoteNode)
            }
        }catch (e: NoSuchElementException){
            val error = SimpleJSONRPCError(SimpleJSONRPCError.ERROR_CODE.METHOD_NOT_FOUND, "No such method", "")
            JSONRPCResponse(null, error, request.id)
        }catch (e: IllegalArgumentException){
            val error = SimpleJSONRPCError(SimpleJSONRPCError.ERROR_CODE.INVALID_PARAMETERS, "No such method", "")
            JSONRPCResponse(null, error, request.id)
        }
    }


    fun plug(plugin: JSONRPCPlugin){
        this.plugins.add(plugin)
    }

    fun unplug(plugin: JSONRPCPlugin){
        this.plugins.remove(plugin)
    }
}