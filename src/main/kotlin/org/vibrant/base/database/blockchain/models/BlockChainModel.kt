package org.vibrant.example.chat.base.models

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import org.vibrant.base.database.blockchain.BaseBlockModel
import org.vibrant.core.models.BlockChainModel

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonTypeName("blockchain")
open class BlockChainModel(
        val blocks: List<BaseBlockModel>
): BlockChainModel()