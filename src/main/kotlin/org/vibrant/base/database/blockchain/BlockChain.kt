package org.vibrant.base.database.blockchain

import org.vibrant.base.database.blockchain.models.BlockChainModel
import org.vibrant.base.database.blockchain.models.BlockModel
import org.vibrant.core.ModelProducer


abstract class BlockChain<B: BlockModel, out T: BlockChainModel> : ModelProducer<T>(){

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

    /**
     * @return true if chain is good
     */
    abstract fun checkIntegrity(): Boolean

    abstract fun createGenesisBlock(): B

    protected fun notifyNewBlock(){
        this.listeners.forEach{ it.nextBlock( this.latestBlock()) }
    }

    interface NewBlockListener<in B: BlockModel>{
        fun nextBlock(blockModel: B)
    }
}