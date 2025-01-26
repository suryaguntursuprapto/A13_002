package com.example.tugasakhirpam.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhirpam.ui.view.HalamanAwalKonser
import com.example.tugasakhirpam.ui.view.event.HomeEventScreen
import com.example.tugasakhirpam.ui.view.peserta.HomePesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.DetailPesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.EntryPesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.UpdatePesertaForm
import com.example.tugasakhirpam.ui.view.peserta.UpdatePesertaView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHalamanAwalKonser.route, // Set Halaman Awal Konser sebagai startDestination
        modifier = Modifier
    ) {
        // Halaman Awal Konser
        composable(DestinasiHalamanAwalKonser.route) {
            HalamanAwalKonser(navController = navController)
        }

        // Home Peserta Screen
        composable(DestinasiHomePeserta.route) {
            HomePesertaScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPeserta.route) },
                onDetailClick = { idPeserta ->
                    navController.navigate(DestinasiDetailPeserta.route.replace("{id_peserta}", idPeserta))
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // Entry Peserta Screen
        composable(DestinasiInsertPeserta.route) {
            EntryPesertaScreen(
                navigateBack = {
                    navController.popBackStack(DestinasiHomePeserta.route, inclusive = false)
                }
            )
        }

        // Detail Peserta Screen
        composable(
            route = DestinasiDetailPeserta.route,
            arguments = listOf(navArgument(DestinasiDetailPeserta.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString(DestinasiDetailPeserta.idArg) ?: ""
            DetailPesertaScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id ->
                    navController.navigate(DestinasiEditPeserta.createRoute(id))
                },
                id = idPeserta
            )
        }

        // Update Peserta Screen
        composable(
            route = DestinasiEditPeserta.route,
            arguments = listOf(navArgument(DestinasiEditPeserta.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPeserta = backStackEntry.arguments?.getString(DestinasiEditPeserta.idArg) ?: ""
            UpdatePesertaView(
                idPeserta = idPeserta,
                navigateBack = { navController.navigateUp() }
            )
        }

        // EVENT
        composable(DestinasiHomeEvent.route) {
            HomeEventScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPeserta.route) },
                onDetailClick = { idEvent ->
                    navController.navigate(DestinasiDetailPeserta.route.replace("{id_event}", idEvent))
                },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

