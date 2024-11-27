package com.shopify.checkout_sdk_mobile_buy_integration_sample.products.product.data

import com.shopify.checkout_sdk_mobile_buy_integration_sample.products.product.data.source.network.ProductsStorefrontApiClient
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.suspendCoroutine

class ProductRepository(
    private val client: ProductsStorefrontApiClient,
) {

    suspend fun getProduct(productId: ID): Flow<Product> {
        return suspendCoroutine { continuation ->
            client.fetchProduct(productId = productId, numVariants = 1, { result ->
                val product = result.data?.product
                if (product == null) {
                    continuation.resumeWith(Result.failure(RuntimeException("Failed to fetch product")))
                } else {
                    continuation.resumeWith(
                        Result.success(
                            flowOf(product.toLocal())
                        )
                    )
                }
            }, { exception ->
                continuation.resumeWith(Result.failure(exception))
            })
        }
    }

    suspend fun getProducts(numProducts: Int, numVariants: Int, cursor: String?): Flow<Products> {
        return suspendCoroutine { continuation ->
            client.fetchProducts(numProducts = numProducts, numVariants = numVariants, cursor = cursor, { response ->
                val products = response.data?.products
                if (products == null) {
                    continuation.resumeWith(Result.failure(RuntimeException("Failed to fetch products")))
                } else {
                    val uiProducts = Products(
                        products = products.edges.map { it.node.toLocal() },
                        pageInfo = PageInfo(
                            startCursor = products.pageInfo.startCursor,
                            endCursor = products.pageInfo.endCursor,
                        )
                    )
                    continuation.resumeWith(Result.success(flowOf(uiProducts)))
                }
            }, { exception ->
                continuation.resumeWith(Result.failure(exception))
            })
        }
    }
}
