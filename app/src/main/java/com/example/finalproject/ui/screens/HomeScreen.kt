package com.example.finalproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            // Tu peux ajouter un AppBar ici si tu veux
            Text(text = "Bienvenue dans l'application des départements français")
        },
        content = {
            Column(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                Button(onClick = {
                    // Navigation vers le quiz Nom → Numéro
                    navController.navigate("quiz_nom_to_num")
                }) {
                    Text(text = "Quiz: Nom → Numéro")
                }

                Button(onClick = {
                    // Navigation vers le quiz Numéro → Nom
                    navController.navigate("quiz_num_to_nom")
                }) {
                    Text(text = "Quiz: Numéro → Nom")
                }

                Button(onClick = {
                    // Navigation vers le quiz Mélangé
                    navController.navigate("quiz_mixed")
                }) {
                    Text(text = "Quiz: Mode Mélangé")
                }

                // Ajoute ici d'autres boutons si tu veux plus de modes
            }
        }
    )
}
