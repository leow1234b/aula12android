package com.example.aula12

import android.app.FragmentManager.BackStackEntry
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aula12.ui.theme.Aula12Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                LayoutMain()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela1(navController : NavController){

    var nome  by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Tela1", fontSize = 25.sp )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(value = nome, onValueChange = {nome = it}, label = { Text(text = "nome do usuario")})

        Spacer(modifier = Modifier.height(15.dp))

        TextField(value = email, onValueChange = {email = it}, label = { Text(text = "e-mail")})


        Button(onClick = {
             if(nome.isNotBlank() && email.isNotBlank()){
                 navController.navigate("tela2/$nome/$email")
             }
            else{
                Toast.makeText(
                    context,
                    "preencha todos os dados",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text(text = "tela1")
        }
    }
}

@Composable
fun Tela2(navController : NavController, nome : String?, email: String?){

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        Text(text = "bem vindo, $nome!", fontSize = 25.sp )
        Text(text = "($email)")

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "voltar para tela1")
        }
    }
}

@Composable
fun LayoutMain(){

    var  navController = rememberNavController()


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        NavHost(navController = navController, startDestination = "tela1"){
            composable("tela1"){ Tela1(navController = navController)}
            composable("tela2/{nome}/{email}"){
                //Tela2(navController = navController)
                backStackEntry -> Tela2(
                navController = navController,
                nome = backStackEntry.arguments?.getString("nome"),
                email = backStackEntry.arguments?.getString("email"))
            }
        }

    }
}
