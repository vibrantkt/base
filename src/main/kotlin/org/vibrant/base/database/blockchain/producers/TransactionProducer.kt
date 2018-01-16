package org.vibrant.base.database.blockchain.producers

import org.vibrant.base.database.blockchain.models.BlockModel
import org.vibrant.core.ModelProducer

abstract class TransactionProducer<out T: BlockModel>: ModelProducer<T>()