package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.finalproject.ui.screens.InverseQuizScreen
import com.example.finalproject.ui.screens.QuizScreen
import com.example.finalproject.ui.theme.FinalProjectTheme

val bottomNavigationHeight = 56.dp // Taille maximale de la barre de navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                val navController = rememberNavController()

                // Ajouter les écrans et la barre de navigation
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController, bottomNavigationHeight) }
                ) { paddingValues ->
                    // Définir les écrans à afficher en prenant en compte le padding
                    NavigationHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues),
                        bottomHeight = bottomNavigationHeight
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier, bottomHeight: Dp){
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, modifier) }
        composable("learning") { LearningScreen(navController, bottomHeight) }
        composable("quiz_nom_to_num") { QuizScreen(navController) }
        composable("quiz_num_to_nom") { InverseQuizScreen((navController)) }
        composable("quiz_mixed") { /* Composable pour quiz Mode Mélangé */ }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController, bottomNavigationHeight: Dp) {
    NavigationBar(
        modifier = Modifier.height(bottomNavigationHeight) // Applique la hauteur maximale ici
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text(stringResource(R.string.home)) },
            selected = false,
            onClick = {
                navController.navigate("home") // Navigation vers l'écran d'accueil
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.School, contentDescription = "Apprentissage") },
            label = { Text(stringResource(R.string.learning)) },
            selected = false,
            onClick = {
                navController.navigate("learning") // Navigation vers l'écran d'apprentissage
            }
        )
    }
}