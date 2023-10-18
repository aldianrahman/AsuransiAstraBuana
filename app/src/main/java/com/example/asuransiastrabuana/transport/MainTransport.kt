package com.example.asuransiastrabuana.transport

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.koushikdutta.async.future.Future
import com.koushikdutta.ion.Ion

@Suppress("NAME_SHADOWING")
class MainTransport : IonMaster() {
    private val sendObject = JsonObject()

    fun getData(context: Context?, callback: IonCallback?): java.util.concurrent.Future<JsonObject>? {

        val returnObj: Future<JsonObject> = Ion.with(context)
            .load("https://pokeapi.co/api/v2/pokemon/")
            .setLogging("IONLOG", Log.DEBUG)
            .asJsonObject()
        returnObj.setCallback(getJsonFutureCallback(context!!, callback!!))
        return returnObj
    }

    fun getDataDetail(s:String,context: Context?, callback: IonCallback?): java.util.concurrent.Future<JsonObject>? {

        val returnObj: Future<JsonObject> = Ion.with(context)
            .load("https://pokeapi.co/api/v2/pokemon/"+s)
            .setLogging("IONLOG", Log.DEBUG)
            .asJsonObject()
        returnObj.setCallback(getJsonFutureCallback(context!!, callback!!))
        return returnObj
    }

}