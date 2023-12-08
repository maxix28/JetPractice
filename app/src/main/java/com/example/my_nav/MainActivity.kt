package com.example.my_nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.my_nav.Utils.BottomNav
import com.example.my_nav.Utils.ScreenA
import com.example.my_nav.Utils.ScreenB
import com.example.my_nav.Utils.ScreenC
import com.example.my_nav.Utils.ScreenD
import com.example.my_nav.Utils.ScreenList
import com.example.my_nav.ui.theme.My_NAVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My_NAVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myNav()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myNav(modifier: Modifier= Modifier){
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
val currentScreen = ScreenList.find{it.route == currentDestination?.route} ?: ScreenA
    //val currentScreen = ScreenList.find { it.route == currentDestination?.route } ?: ScreenA

    Scaffold(
        bottomBar = { BottomNav(navController,  currentScreen = currentScreen)

        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = ScreenA.route,modifier = Modifier.padding(innerPadding)){
            composable(route = ScreenA.route){
                ScreenA (onClicklistener = {
                    navController.navigate(ScreenB.route)
                    println("Navigating to B")
                })
            }
            composable(route = ScreenB.route){
                ScreenB (onClicklistener = {
                    navController.navigate(ScreenC.route)
                    println("Navigating to C")
                })
            }
            composable(route = ScreenC.route){
                ScreenC (onClicklistener = {
                    navController.navigate(ScreenA.route)
                    println("Navigating to A")
                })
            }
            composable(route = ScreenD.route){
                ScreenD (onClicklistener = {
                    navController.navigate(ScreenA.route)
                    println("Navigating to A")
                })
            }
        }
    }
//    NavHost(navController = navController, startDestination = ScreenA.route){
//        composable(route = ScreenA.route){
//            ScreenA (onClicklistener = {
//                navController.navigate(ScreenB.route)
//                println("Navigating to B")
//            })
//        }
//        composable(route = ScreenB.route){
//            ScreenB (onClicklistener = {
//                navController.navigate(ScreenC.route)
//                println("Navigating to C")
//            })
//        }
//        composable(route = ScreenC.route){
//            ScreenC (onClicklistener = {
//                navController.navigate(ScreenA.route)
//                println("Navigating to A")
//            })
//        }
//        composable(route = ScreenD.route){
//            ScreenC (onClicklistener = {
//                navController.navigate(ScreenA.route)
//                println("Navigating to A")
//            })
//        }
//    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    My_NAVTheme {
        Greeting("Android")
    }
}