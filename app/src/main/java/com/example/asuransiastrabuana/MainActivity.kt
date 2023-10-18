package com.example.asuransiastrabuana

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.asuransiastrabuana.db.DatabaseHelper
import com.example.asuransiastrabuana.screen.DetailItem
import com.example.asuransiastrabuana.screen.HomePage
import com.example.asuransiastrabuana.screen.SplashScreen
import com.example.asuransiastrabuana.transport.IonMaster
import com.example.asuransiastrabuana.transport.MainTransport
import com.example.asuransiastrabuana.ui.theme.AsuransiAstraBuanaTheme
import com.example.asuransiastrabuana.util.ScreenRoute
import com.example.asuransiastrabuana.util.Util
import com.google.gson.JsonObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsuransiAstraBuanaTheme {
                lateinit var dbHelper: DatabaseHelper
                lateinit var database: SQLiteDatabase
                val TAG = "MAIN_ACTIVITY_DEBUG"
                dbHelper = DatabaseHelper(this)
                database = dbHelper.writableDatabase
                val context = LocalContext.current
                val mainTransport = MainTransport()


                mainTransport.getData(context,object : IonMaster.IonCallback {
                    @SuppressLint("Range")
                    override fun onReadyCallback(errorMessage: String?, `object`: Any?) {
                        if (errorMessage == null){
                            Log.d(TAG, "onSuccess: $`object`")
                            val result = `object` as JsonObject
                            val array = result.asJsonObject.get("results").asJsonArray
                            val values = ContentValues()
                            val cursor = database.query("MyTable", null, null, null, null, null, null)
                            val tableIsEmpty = cursor.count == 0
                            if(!tableIsEmpty){
                                database.delete("MyTable",null,null)
                            }
                            for (i in 0 until array.size()){
                                val name = array[i].asJsonObject.get("name").asString
                                val url = array[i].asJsonObject.get("url").asString
                                insertOrReplaceData(context,name,url)
                            }
                        }else{
                            Log.d(TAG, "onError: $errorMessage")
                            Util.toastToText(context,"Kesalahan pada sistem")
                        }
                    }

                })
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.SplashScreen.route
                ){

                    composable(ScreenRoute.SplashScreen.route){
                        SplashScreen(navController,context)
                    }

                    composable(ScreenRoute.HomePage.route){
                        HomePage(navController,context)
                    }

                    composable(ScreenRoute.DetailItem.route+"/{itemId}"){backStackEntry ->

                        val id = backStackEntry.arguments?.getString("itemId")
                        var jsonObject = JsonObject()

                        if (id != null) {
                            mainTransport.getDataDetail(id,context,object : IonMaster.IonCallback {
                                @SuppressLint("Range")
                                override fun onReadyCallback(errorMessage: String?, `object`: Any?) {
                                    if (errorMessage == null){
                                        Log.d(TAG, "onSuccess: $`object`")
                                    }else{
                                        Log.d(TAG, "onError: $errorMessage")
                                        Util.toastToText(context,"Kesalahan pada sistem")
                                    }
                                }
                            })
                        }


                        DetailItem(navController,context,jsonObject)


                    }

                }
            }
        }
    }
}

fun insertOrReplaceData(context: Context, name: String, url: String) {
    val dbHelper = DatabaseHelper(context)
    val db = dbHelper.writableDatabase

    val values = ContentValues()
    values.put("name", name)
    values.put("url", url)

    // Use CONFLICT_REPLACE for INSERT OR REPLACE
    db.insertWithOnConflict("MyTable", null, values, SQLiteDatabase.CONFLICT_REPLACE)

    db.close()
}