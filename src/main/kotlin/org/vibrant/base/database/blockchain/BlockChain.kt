package org.vibrant.base.database.blockchain

import org.vibrant.core.models.BlockModel
import org.vibrant.example.chat.base.models.BlockChainModel
import org.vibrant.core.producers.BlockChainProducer


/**
 * @property blocks Ordered list of blocks from genesis to the last
 */
abstract class BlockChain<B: BlockModel, T: BlockChainModel> : BlockChainProducer<T>(){

    private val listeners = arrayListOf<NewBlockListener<B>>()

    /**
     * Add listener
     */
    fun addNewBlockListener(newBlockListener: NewBlockListener<B>){
        this.listeners.add(newBlockListener)
    }

    /**
     * Remove listener
     */
    fun removeNewBlockListener(newBlockListener: NewBlockListener<B>){
        this.listeners.add(newBlockListener)
    }

    /**
     * Get last block of local chain
     * @return last block in local chain
     */
    abstract fun latestBlock(): B

    /**
     * Append block to the end of local blockchain
     */
    abstract fun addBlock(block: B): B

    abstract fun checkIntegrity(): Boolean

    abstract fun createGenesisBlock(): B

    protected fun notifyNewBlock(){
        this.listeners.forEach{ it.nextBlock( this.latestBlock()) }
    }

    interface NewBlockListener<in B: BlockModel>{
        fun nextBlock(blockModel: B)
    }
}