package org.vibrant.base.database.blockchain

import org.vibrant.core.ModelSerializer
import org.vibrant.core.models.BlockModel
import org.vibrant.example.chat.base.models.BlockChainModel

abstract class InMemoryBlockChain<B: BlockModel, T: BlockChainModel>: BlockChain<B, T>() {

    protected val blocks = arrayListOf(this.createGenesisBlock())

    override fun latestBlock(): B = this.blocks.last()

    override fun addBlock(block: B): B {
        this.blocks.add(block)
        this.notifyNewBlock()
        return this.latestBlock()
    }
}