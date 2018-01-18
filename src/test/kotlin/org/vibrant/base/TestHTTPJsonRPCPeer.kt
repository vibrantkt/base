package org.vibrant.base


import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.vibrant.base.http.HTTPJsonRPCPeer
import org.vibrant.base.http.HTTPPeer
import org.vibrant.base.http.HTTPPeerConfig
import org.vibrant.base.http.Request
import org.vibrant.base.rpc.JSONRPCMethod
import org.vibrant.base.rpc.json.JSONRPC
import org.vibrant.base.rpc.json.JSONRPCDefaultProtocol
import org.vibrant.base.rpc.json.JSONRPCRequest
import org.vibrant.base.rpc.json.SimpleJSONRPCError
import org.vibrant.core.node.RemoteNode
import java.net.Socket
import kotlin.coroutines.experimental.suspendCoroutine

class TestHTTPJsonRPCPeer {

    val config = object: HTTPPeerConfig("rpc"){}

    private val rpc = JSONRPCDefaultProtocol()

    private fun createPeer(): HTTPJsonRPCPeer {
        var port = 5000
        while(true){
            port++
            try {
                Socket("localhost", port).close()
            }catch (e: Exception){
                val peer = HTTPJsonRPCPeer(port, rpc)
                peer.start()
                return peer
            }
        }

    }

    @Test
    fun `Test handle request`(){
        val peer1 = createPeer()

        val some = peer1.request(
                RemoteNode("localhost", peer1.port),
                JSONRPCRequest("connect", arrayOf(), 1L))
        assertEquals(
                true,
                some.result
        )
        assertEquals(
                null,
                some.error
        )
        assertEquals(
                1,
                some.id
        )
        assertEquals(
                "2.0",
                some.version
        )

        val errorSome = peer1.request(
                RemoteNode("localhost", peer1.port),
                JSONRPCRequest("disconnect", arrayOf(), 2L)
        )

        assertNotEquals(
                null,
                errorSome.error
        )
        assertEquals(
                SimpleJSONRPCError.ERROR_CODE.METHOD_NOT_FOUND,
                errorSome.error!!.code
        )


    }
}