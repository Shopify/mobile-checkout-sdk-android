package com.shopify.checkout_sdk_mobile_buy_integration_sample.products.collection.data

import com.shopify.checkout_sdk_mobile_buy_integration_sample.products.collection.data.source.network.CollectionsStorefrontApiClient
import kotlin.coroutines.suspendCoroutine

class CollectionRepository(
    private val client: CollectionsStorefrontApiClient,
) {
    suspend fun getCollections(numberOfCollections: Int, numberOfProductsPerCollection: Int): List<Collection> {
        return suspendCoroutine { continuation ->
            client.fetchCollections(numberOfCollections, numberOfProductsPerCollection, {
                val collections = it.data?.collections
                if (collections == null) {
                    continuation.resumeWith(Result.failure(RuntimeException("Failed to fetch collections")))
                } else {
                    continuation.resumeWith(Result.success(collections.nodes.map { it.toLocal() }))
                }
            }, { exception ->
                continuation.resumeWith(Result.failure(exception))
            })
        }
    }

    suspend fun getCollection(collectionHandle: String, numberOfProducts: Int): Collection {
        return suspendCoroutine { continuation ->
            client.fetchCollection(collectionHandle, numberOfProducts, {
                val collection = it.data?.collection
                if (collection == null) {
                    continuation.resumeWith(Result.failure(RuntimeException("Failed to fetch collection")))
                } else {
                    continuation.resumeWith(Result.success(collection.toLocal()))
                }
            }, { error ->
                continuation.resumeWith(Result.failure(error))
            })
        }
    }
}
