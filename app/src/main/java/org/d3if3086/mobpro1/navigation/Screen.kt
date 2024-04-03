package org.d3if3086.mobpro1.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Add: Screen("addScreen")
    data object About: Screen("aboutScreen")
}