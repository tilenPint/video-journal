package com.tilenpint.minivideojournalapp.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tilenpint.minivideojournalapp.ui.screen.camera.CameraScreen
import com.tilenpint.minivideojournalapp.ui.screen.camera.CameraViewModelImpl
import com.tilenpint.minivideojournalapp.ui.screen.home.HomeScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VideoNavGraph() {
    val navController = rememberNavController()

    val cameraPermissionActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (!it) return@rememberLauncherForActivityResult
            navController.navigate(CameraScreenKey)
        }
    )

    NavHost(navController = navController, startDestination = HomeScreenKey) {
        composable<HomeScreenKey> {
            HomeScreen(
                viewModel = koinViewModel(),
                navigateToCamera = { cameraPermissionActivity.launch(android.Manifest.permission.CAMERA) }
            )
        }
        composable<CameraScreenKey> {
            CameraScreen(
                viewModel = koinViewModel<CameraViewModelImpl>(),
                backNavigation = { navController.popBackStack() }
            )
        }
    }
}



@Serializable
data object HomeScreenKey

@Serializable
data object CameraScreenKey