package com.example.tugasakhirpam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhirpam.ui.view.peserta.HomePesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.DetailPesertaScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePeserta.route,  // Pastikan ini benar
        modifier = Modifier
    ) {
        // Home Peserta Screen
        composable(DestinasiHomePeserta.route) {
            HomePesertaScreen(
                { navController.navigate(DestinasiInsertPeserta.route) },
                onDetailClick = { idPeserta ->
                    navController.navigate("detail_peserta/$idPeserta")  // Format navigasi sudah benar
                }
            )
        }

        // Detail Peserta Screen
        composable(
            route = "detail_peserta/{id_peserta}",
            arguments = listOf(navArgument("id_peserta") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString("id_peserta") ?: ""
            DetailPesertaScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id -> navController.navigate("edit_peserta/$id") },
                id = idPeserta,
            )
        }
    }
}
