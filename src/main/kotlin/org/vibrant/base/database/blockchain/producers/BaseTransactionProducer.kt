package org.vibrant.base.database.blockchain.producers

import org.vibrant.base.database.blockchain.models.TransactionPayload
import org.vibrant.core.ModelSerializer
import org.vibrant.core.algorithm.SignatureProducer
import org.vibrant.base.database.blockchain.models.BaseTransactionModel
import org.vibrant.core.producers.TransactionProducer
import org.vibrant.example.chat.base.util.HashUtils
import java.security.KeyPair


/***
 * [BaseTransactionModel] producer class
 *
 * @property from address of sender
 * @property to address of receiver
 * @property payload transaction payload
 * @property keyPair keyPair, which will be used to create signature
 * @property signatureProducer create signature
 *
 */
open class BaseTransactionProducer(
        private val from: String,
        private val to: String,
        private val payload: TransactionPayload,
        private val keyPair: KeyPair,
        private val signatureProducer: SignatureProducer
): TransactionProducer<BaseTransactionModel>() {
    override fun produce(serializer: ModelSerializer): BaseTransactionModel {
        return BaseTransactionModel(
                from,
                to,
                payload,
                HashUtils.bytesToHex(
                        signatureProducer.produceSignature((this.from + this.to + String(serializer.serialize(this.payload))).toByteArray(), keyPair)
                )
        )
    }
}
