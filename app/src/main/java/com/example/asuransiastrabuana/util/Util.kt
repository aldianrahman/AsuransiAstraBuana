package com.example.asuransiastrabuana.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

class Util {

    companion object {
        fun toastToText(context: Context, s: String) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }
    }

}