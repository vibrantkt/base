package org.vibrant.base.http


import com.github.kittinunf.fuel.httpPost
import io.javalin.Javalin
import mu.KotlinLogging
import org.vibrant.core.node.AbstractPeer
import org.vibrant.core.node.RemoteNode
import java.io.ByteArrayInputStream

abstract class HTTPPeer(val port: Int, val config: HTTPPeerConfig): AbstractPeer(){

    protected val logger = KotlinLogging.logger{}

    private val server = createServer()


    private fun createServer(): Javalin {
        return Javalin
                .create()
                .post(config.endpoint, { ctx ->
                    ctx.result(
                            ByteArrayInputStream(
                                    this.handleRequest(Request(
                                            request = ctx.request(),
                                            body = ctx.bodyAsBytes(),
                                            headers = ctx.headerMap()
                                    ))
                            )
                    )
                })
                .port(port)
    }

    abstract fun handleRequest(request: Request): ByteArray

    override fun start() {
        server.start()
    }

    override fun stop() {
        server.stop()
    }


    override fun request(byteArray: ByteArray, remoteNode: RemoteNode): ByteArray {
        val(_, _, result) =  "http://${remoteNode.address}:${remoteNode.port}/rpc"
                .httpPost()
                .header("peer-port" to port)
                .body(String(byteArray))
                .response()

        return result.get()
    }

    abstract fun connect(remoteNode: RemoteNode)

}