package org.vibrant.base.database.blockchain.models

abstract class ClassicBlock(val index: Long, val hash: String, val previousHash: String): BlockModel()