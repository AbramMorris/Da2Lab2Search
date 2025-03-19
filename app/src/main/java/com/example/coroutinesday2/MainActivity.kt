package com.example.coroutinesday2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchScreen()
        }
    }
}@Composable
fun SearchScreen() {
    val allNames = listOf("Abram","morris","helmy","gayed","alooo","aywaaa","meen","anaa")
    val searchQuery = remember { MutableSharedFlow<String>() }
    val coroutineScope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            searchQuery.collect { query ->
                result = allNames.filter { it.startsWith(query, ignoreCase = true) }.joinToString("\n")
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                coroutineScope.launch {
                    searchQuery.emit(it)
                }
            },
            label = {Text("Search")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            if (result.isNotEmpty()) result else "No results found"
        )
    }
}