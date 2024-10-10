/*
 * MIT License
 * 
 * Copyright 2023-present, Shopify Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.shopify.checkout_sdk_mobile_buy_integration_sample.logs.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shopify.checkoutsheetkit.pixelevents.Context
import com.shopify.checkoutsheetkit.pixelevents.CustomPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.PixelEvent
import com.shopify.checkoutsheetkit.pixelevents.AlertDisplayedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.AlertDisplayedPixelEventData
import com.shopify.checkoutsheetkit.pixelevents.CheckoutAddressInfoSubmittedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.CheckoutCompletedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.CheckoutContactInfoSubmittedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.CheckoutShippingInfoSubmittedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.CheckoutStartedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.PageViewedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.PaymentInfoSubmittedPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.UIExtensionErroredPixelEvent
import com.shopify.checkoutsheetkit.pixelevents.UIExtensionErroredPixelEventData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun PixelEventDetails(
    event: PixelEvent?,
    prettyJson: Json,
) {
    val evenModifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.surface)
    val oddModifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.background)

    LogDetails(header = "Event Name", message = event?.name ?: "", evenModifier)
    LogDetails(header = "Timestamp", message = event?.timestamp ?: "", oddModifier)
    LogDetails(header = "ID", message = event?.id ?: "", evenModifier)
    LogDetails(header = "Type", message = event?.type?.name?.lowercase() ?: "", oddModifier)

    when (event) {
        is PageViewedPixelEvent -> {
            LogData("", evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is CheckoutStartedPixelEvent -> {
            LogData(prettyJson.encodeDataToString(event.data), evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is CheckoutContactInfoSubmittedPixelEvent -> {
            LogData(prettyJson.encodeDataToString(event.data), evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is CheckoutAddressInfoSubmittedPixelEvent -> {
            LogData(prettyJson.encodeDataToString(event.data), evenModifier,)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is CheckoutShippingInfoSubmittedPixelEvent -> {
            LogData(prettyJson.encodeDataToString(event.data), evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is PaymentInfoSubmittedPixelEvent -> {
            LogData(prettyJson.encodeDataToString(event.data), evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is CheckoutCompletedPixelEvent -> {
            LogData(prettyJson.encodeDataToString(event.data), evenModifier,)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is AlertDisplayedPixelEvent -> {
            LogData(prettyJson.encodeDataToString<AlertDisplayedPixelEventData>(event.data), evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is UIExtensionErroredPixelEvent -> {
            LogData(prettyJson.encodeDataToString<UIExtensionErroredPixelEventData>(event.data), evenModifier)
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        is CustomPixelEvent -> {
            LogDetails(
                header = "Custom Data",
                message = event.customData ?: "",
                evenModifier,
            )
            LogContext(prettyJson.encodeDataToString<Context>(event.context), oddModifier)
        }
        else -> {}
    }
}

private inline fun <reified T> Json.encodeDataToString(el: T?, default: String = "n/a"): String {
    if (el == null) return default
    return encodeToString(el)
}

@Composable
fun LogData(message: String, modifier: Modifier) {
    LogDetails(
        header = "Data",
        message = message,
        modifier = modifier
    )
}

@Composable
fun LogContext(message: String, modifier: Modifier) {
    LogDetails(
        header = "Context",
        message = message,
        modifier = modifier
    )
}
