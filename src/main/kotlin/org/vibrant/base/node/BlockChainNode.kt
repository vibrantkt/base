package org.vibrant.base.node

import org.vibrant.base.database.blockchain.models.BlockChainModel

interface BlockChainNode<T: BlockChainModel> {
    val chainModel: T
}