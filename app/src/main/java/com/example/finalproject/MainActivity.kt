package com.example.finalproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.screens.LearningScreen
import com.example.finalproject.ui.screens.HomeScreen
import com.example.finalproject.ui.screens.NameToNumQuizScreen
import com.example.finalproject.ui.screens.NumToNameQuizScreen
import com.example.finalproject.ui.theme.FinalProjectTheme

val bottomNavigationHeight = 56.dp // Max size for the navigation bottom bar

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController, bottomNavigationHeight) }
                ) {
                    NavigationHost(
                        navController = navController,
                        bottomHeight = bottomNavigationHeight
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, bottomHeight: Dp){
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("learning") { LearningScreen(bottomHeight) }
        composable("quiz_name_to_num") { NameToNumQuizScreen(bottomHeight = bottomHeight) }
        composable("quiz_num_to_name") { NumToNameQuizScreen(bottomHeight = bottomHeight) }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController, bottomNavigationHeight: Dp) {
    NavigationBar(
        modifier = Modifier.height(bottomNavigationHeight)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text(stringResource(R.string.home)) },
            selected = false,
            onClick = {
                navController.navigate("home")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.School, contentDescription = "Apprentissage") },
            label = { Text(stringResource(R.string.learning)) },
            selected = false,
            onClick = {
                navController.navigate("learning")
            }
        )
    }
}