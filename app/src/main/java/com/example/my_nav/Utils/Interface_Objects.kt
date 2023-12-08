package com.example.my_nav.Utils

interface ScreenNav{
    val route :String
}

object ScreenA: ScreenNav{
    override val route = "ScreenA"

}
object ScreenB: ScreenNav{
    override val route = "ScreenB"

}
object ScreenC: ScreenNav{
    override val route = "ScreenC"

}
object ScreenD: ScreenNav{
    override val route = "ScreenD"

}
val ScreenList = listOf(ScreenA,ScreenB,ScreenC,ScreenD)