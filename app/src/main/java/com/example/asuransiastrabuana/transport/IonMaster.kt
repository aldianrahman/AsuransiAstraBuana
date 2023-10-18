package com.example.asuransiastrabuana.transport

import android.content.Context
import android.util.Log
import com.example.asuransiastrabuana.R
import com.example.asuransiastrabuana.util.Util
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.koushikdutta.async.future.FutureCallback
import java.util.concurrent.CancellationException

open class IonMaster {

    fun getJsonFutureCallback(
        context: Context,
        callback: IonCallback
    ): FutureCallback<JsonObject> {
        return FutureCallback { e, jsonObject ->
            Log.i("JSON_UPLOAD", "getJsonFutureCallback r: $jsonObject")
            Log.i("JSON_UPLOAD", "getJsonFutureCallback e: $e")
            if (e != null) {
                if (e is CancellationException) {
                    callback.onReadyCallback(
                        context.getString(R.string.error_when_processing_your_data),
                        e
                    )
                } else if (Util.isNetworkAvailable(context)) {
                    callback.onReadyCallback(
                        context.getString(R.string.error_when_processing_your_data),
                        null
                    )
                } else {
                    callback.onReadyCallback(
                        context.getString(R.string.check_your_internet_connection),
                        null
                    )
                }
                Log.d("ION_MASTER", "onError: $e")
            } else {
                Log.d("ION_MASTER", "onSuccess: $jsonObject")
                callback.onReadyCallback(errorMessage = null,jsonObject)
                if (jsonObject["results"] != null) {
                    if (jsonObject["results"] is JsonObject ||
                        jsonObject["results"] is JsonArray ||
                        jsonObject["results"] is JsonPrimitive)
                    {
                        callback.onReadyCallback(errorMessage = null,jsonObject)

                    }else {
                        callback.onReadyCallback("Error ","Error")
                    }
                }
            }
        }
    }

    interface IonCallback {
        fun onReadyCallback(errorMessage: String?, `object`: Any?)
    }
}