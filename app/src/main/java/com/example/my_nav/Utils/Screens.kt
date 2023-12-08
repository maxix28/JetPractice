package com.example.my_nav.Utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScreenA(modifier: Modifier = Modifier,
            onClicklistener:()->Unit)
{
    Column(
        modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center,Alignment.CenterHorizontally){
        Text("This is Screen A",fontSize = 20.sp, fontWeight = FontWeight.Bold,)
//        Button(onClick = onClicklistener){
//            Text("Navigate to B")
//        }
    }
}
@Composable
fun ScreenB(modifier: Modifier = Modifier,
            onClicklistener:()->Unit)
{
    Column(
        modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
           ,
        verticalArrangement = Arrangement.Center,Alignment.CenterHorizontally){
        Text("This is Screen B",fontSize = 20.sp, fontWeight = FontWeight.Bold,)
//        Button(onClick = onClicklistener, modifier = modifier){
//            Text("Navigate to C")
//        }
    }
}
@Composable
fun ScreenC(modifier: Modifier = Modifier,
            onClicklistener:()->Unit)
{
    Column(
        modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            ,verticalArrangement = Arrangement.Center, Alignment.CenterHorizontally){
        Text("This is Screen C",fontSize = 20.sp, fontWeight = FontWeight.Bold,)
//        Button(onClick = onClicklistener){
//            Text("Navigate to A")
//        }
    }
}
private val SaveMap = mutableMapOf<String, KeyParams>()

private data class KeyParams(
    val params: String = "",
    val index: Int,
    val scrollOffset: Int
)

@Composable
fun rememberForeverLazyListState(
    key: String,
    params: String = "",
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    val scrollState = rememberSaveable(saver = LazyListState.Saver) {
        var savedValue = SaveMap[key]
        if (savedValue?.params != params) savedValue = null
        val savedIndex = savedValue?.index ?: initialFirstVisibleItemIndex
        val savedOffset = savedValue?.scrollOffset ?: initialFirstVisibleItemScrollOffset
        LazyListState(
            savedIndex,
            savedOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            val lastIndex = scrollState.firstVisibleItemIndex
            val lastOffset = scrollState.firstVisibleItemScrollOffset
            SaveMap[key] = KeyParams(params, lastIndex, lastOffset)
        }
    }
    return scrollState
}
@Composable
fun ScreenD(modifier: Modifier = Modifier,
            onClicklistener:()->Unit)
{

    var listState = rememberLazyListState()
    Column(
        modifier
            .fillMaxHeight(1f)
            .fillMaxWidth()
        ,verticalArrangement = Arrangement.Center, Alignment.CenterHorizontally){
        Text("This is Screen D",fontSize = 20.sp, fontWeight = FontWeight.Bold,)
//        Button(onClick = onClicklistener){
//            Text("Navigate to A")
//        }
        LazyColumn(state = rememberForeverLazyListState(key = "ScreenD")){
            items(100){
                Text(text = it.toString())
            }
        }
    }
}




@Composable
fun BottomNav(navController: NavController, currentScreen: ScreenNav){
    Row(horizontalArrangement  =  Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)){
        ScreenList.forEach { screen ->
            BottomItem(screen.route, { navController.navigate(screen.route) }, currentScreen == screen)

        }
//        BottomItem("ScreenA",{ navController.navigate(ScreenA.route)}, true)
//        BottomItem("ScreenB",{ navController.navigate(ScreenB.route)}, true )
//        BottomItem("ScreenC",{ navController.navigate(ScreenC.route)}, false)
    }
 //   BottomItem("la",{}, false)
}

@Composable
fun BottomItem(
    text: String,
    onSelected:()->Unit,
    selected : Boolean
){

    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) 100 else 150
    val animSpec = remember{
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = 150
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = 0.6f),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
    ){
        //fontSize = 20.sp, fontWeight = FontWeight.Bold,
        Column(){
            Text(text = text,color = tabTintColor, )
            if (selected) {
                Spacer(Modifier.height(7.dp))
                Text(text = "Selected",color = tabTintColor.copy(alpha = 0.7f), fontSize = 12.sp)


            }
        }


    }
}