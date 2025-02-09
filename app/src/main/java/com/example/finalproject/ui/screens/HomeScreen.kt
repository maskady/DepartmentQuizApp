package com.example.finalproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.finalproject.R

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(navController: NavController, modifier : Modifier) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttonModifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(8.dp)

            Button(
                onClick = { navController.navigate("quiz_nom_to_num") },
                modifier = buttonModifier
            ) {
                Text(stringResource(R.string.quiz_1))
            }

            Button(
                onClick = { navController.navigate("quiz_num_to_nom") },
                modifier = buttonModifier
            ) {
                Text(stringResource(R.string.quiz_2))
            }

            Button(
                onClick = { navController.navigate("quiz_mixed") },
                modifier = buttonModifier
            ) {
                Text(stringResource(R.string.quiz_3))
            }
        }
    }
}

