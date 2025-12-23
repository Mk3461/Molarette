package com.example.color_paletteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // 🛑 EKLEDİK
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.color_paletteapp.domain.repository.ColorRepository
import com.example.color_paletteapp.presentation.colorlist.ColorListScreen
import com.example.color_paletteapp.presentation.colorlist.ColorListViewModel
import com.example.color_paletteapp.presentation.randomcolor.RandomColorScreen
import com.example.color_paletteapp.presentation.randomcolor.RandomColorViewModel
import com.example.color_paletteapp.presentation.search.SearchScreen
import com.example.color_paletteapp.presentation.search.SearchViewModel
import com.example.color_paletteapp.ui.theme.Color_PaletteAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var colorRepository: ColorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // 🛑 installSplashScreen() HER ŞEYDEN ÖNCE ÇAĞRILMALIDIR 🛑
        installSplashScreen()

        super.onCreate(savedInstanceState)

        val app = application as ColorPaletteApp
        colorRepository = app.colorRepository

        setContent {
            Color_PaletteAppTheme {
                AppScreen()
            }
        }
    }

    // --- VIEWMODEL FACTORIES ---

    @Composable
    private fun randomColorViewModel(): RandomColorViewModel {
        return viewModel(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RandomColorViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return RandomColorViewModel(colorRepository) as T
                }
                throw IllegalArgumentException("Bilinmeyen ViewModel Sınıfı: ${modelClass.name}")
            }
        })
    }

    @Composable
    private fun colorListViewModel(): ColorListViewModel {
        return viewModel(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ColorListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ColorListViewModel(colorRepository) as T
                }
                throw IllegalArgumentException("Bilinmeyen ViewModel Sınıfı: ${modelClass.name}")
            }
        })
    }

    @Composable
    private fun searchViewModel(): SearchViewModel {
        return viewModel(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SearchViewModel(colorRepository) as T
                }
                throw IllegalArgumentException("Bilinmeyen ViewModel Sınıfı: ${modelClass.name}")
            }
        })
    }

    // --- ANA EKRAN VE NAVİGASYON ---

    @Composable
    fun AppScreen() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { AppBottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = NavRoutes.Random.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(NavRoutes.Random.route) { RandomColorScreen(viewModel = randomColorViewModel()) }
                composable(NavRoutes.List.route) { ColorListScreen(viewModel = colorListViewModel()) }
                composable(NavRoutes.Search.route) { SearchScreen(viewModel = searchViewModel()) }
            }
        }
    }
}

sealed class NavRoutes(val route: String, val icon: ImageVector, val title: String) {
    object Random : NavRoutes("random", Icons.Filled.AutoFixHigh, "Üret")
    object List : NavRoutes("list", Icons.Filled.List, "Kaydedilenler")
    object Search : NavRoutes("search", Icons.Filled.Search, "Ara")
}

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    val items = listOf(NavRoutes.Random, NavRoutes.List, NavRoutes.Search)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}