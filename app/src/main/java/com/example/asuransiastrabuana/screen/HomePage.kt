package com.example.asuransiastrabuana.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.asuransiastrabuana.db.DatabaseHelper
import com.example.asuransiastrabuana.util.ScreenRoute
import kotlin.random.Random

@Composable
fun HomePage(navController: NavHostController, context: Context) {
        ListItem(context,navController)
}

@SuppressLint("Range")
@Composable
fun ListItem(context: Context, navController: NavHostController) {
    val dbHelper = DatabaseHelper(context)
    val db = dbHelper.readableDatabase

    // Perform a database query to retrieve data from MyTable
    val data = mutableListOf<MyTableData>()
    val cursor = db.query(
        DatabaseHelper.TABLE_NAME,
        null, // columns (null selects all columns)
        null, // whereClause
        null, // whereArgs
        null, // groupBy
        null, // having
        null  // orderBy
    )

    while (cursor.moveToNext()) {
        val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
        val url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL))
        data.add(MyTableData(name, url))
    }

    cursor.close()
    db.close()

    var searchText by remember { mutableStateOf("") }

    // Filter data based on searchText
    val filteredData = data.filter { it.name.contains(searchText, ignoreCase = true) }

    Column {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = { newSearch ->
                searchText = newSearch
            }
        )
        LazyColumn {

            items(filteredData) { item ->
                // Display each item in the LazyColumn
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            val itemId = item.name
                            navController.navigate(ScreenRoute.DetailItem.route+"/$itemId") {
                                popUpTo("list") {
                                    inclusive = false
                                }
                            }
                        },
                    colors = CardDefaults.cardColors(getRandomColor())
                ){
                    Text(text = item.name, modifier = Modifier.padding(16.dp).fillMaxWidth())
                }
            }
        }
    }

}

fun getRandomColor(): Color {
    val random = Random.Default
    val red = random.nextFloat()
    val green = random.nextFloat()
    val blue = random.nextFloat()

    val luminance = Color(red, green, blue).luminance()

    // Choose white or black text based on background luminance
    val textColor = if (luminance > 0.5f) Color.Black else Color.White

    return Color(red, green, blue, alpha = 1.0f).copy(alpha = 0.85f) // Adjust alpha as needed
}

data class MyTableData(val name: String, val url: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = { newSearch ->
            onSearchTextChanged(newSearch)
        },
        placeholder = { Text(text = "Search...", color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(color = Color.Black),
        trailingIcon = {
            Row {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.Blue,
                    modifier = Modifier.padding(end = 8.dp)
                )
                if (searchText.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Clear Search",
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            onSearchTextChanged("")
                        }
                    )
                }
            }
        }
    )
}
