package com.paras.kavach.ui.subscription

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.*
import com.paras.kavach.utils.shortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriptionVM : ViewModel() {
    private lateinit var billingClient: BillingClient
    val purchaseSuccessResMLD = MutableLiveData<Boolean>()

    /**
     * Initialize Google Billing Client
     */
    fun initBillingClient(mContext: Context) {
        billingClient = BillingClient.newBuilder(mContext)
            .enablePendingPurchases()
            .setListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        viewModelScope.launch {
                            handlePurchase(purchase)
                        }
                    }
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                    "User Canceled".shortToast(mContext)
                } else {
                    "Something went wrong. Please try again later.".shortToast(mContext)
                }
            }
            .build()
    }

    /**
     * Connect to Billing When User Click on Purchase Button
     */
    fun connectToBilling(activity: SubscriptionActivity, planId: String) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {

            }

            override fun onBillingSetupFinished(p0: BillingResult) {
                viewModelScope.launch {
                    launchBillingFlow(activity, planId)
                }
            }
        })
    }

    private fun launchBillingFlow(activity: SubscriptionActivity, planId: String) {
        Log.e("Plan Id", planId)
        try {
            val queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                    .setProductList(
                        listOf(
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(planId)
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        ).toMutableList()
                    )
                    .build()

            billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                                productDetailsList ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                    Log.d(ContentValues.TAG, "launchBillingFlow: feature not supported")
                } else if (billingResult.responseCode == BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE) {
                    Log.d(ContentValues.TAG, "launchBillingFlow: service unavailable")
                }
                Log.e("productDetails", "" + productDetailsList)

                val productDetails =
                    BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(
                        productDetailsList[0]
                    )
                        .setOfferToken(productDetailsList[0].subscriptionOfferDetails!![0].offerToken)
                        .build()


                val flowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(listOf(productDetails).toMutableList())
                    .build()
                billingClient.launchBillingFlow(activity, flowParams)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
            val ackPurchaseResult = withContext(Dispatchers.IO) {
                billingClient.acknowledgePurchase(
                    acknowledgePurchaseParams.build()
                ) {
                    viewModelScope.launch {
                        queryPurchases()
                    }
                }
            }
        }
    }

    private suspend fun queryPurchases() {
        val queryPurchasesParams =
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()
        val ackPurchaseResult = withContext(Dispatchers.IO) {
            billingClient.queryPurchasesAsync(queryPurchasesParams)
        }
        Log.e("List", "" + ackPurchaseResult.purchasesList)
    }

}