package com.example.tugasakhirpam.navigation

import UpdateEventView
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
import com.example.tugasakhirpam.ui.view.event.DetailEventScreen
import com.example.tugasakhirpam.ui.view.event.EntryEventScreen
import com.example.tugasakhirpam.ui.view.event.HomeEventScreen
import com.example.tugasakhirpam.ui.view.peserta.HomePesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.DetailPesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.EntryPesertaScreen
import com.example.tugasakhirpam.ui.view.peserta.UpdatePesertaForm
import com.example.tugasakhirpam.ui.view.peserta.UpdatePesertaView
import com.example.tugasakhirpam.ui.view.tiket.DetailTiketScreen
import com.example.tugasakhirpam.ui.view.tiket.EntryTiketScreen
import com.example.tugasakhirpam.ui.view.tiket.HomeTiketScreen
import com.example.tugasakhirpam.ui.view.tiket.UpdateTiketScreen
import com.example.tugasakhirpam.ui.view.transaksi.DetailTransaksiScreen
import com.example.tugasakhirpam.ui.view.transaksi.HomeTransaksiScreen
import com.example.tugasakhirpam.ui.view.transaksi.InsertTransaksiView

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
                navigateToItemEntry = { navController.navigate(DestinasiInsertEvent.route) },
                onDetailClick = { idEvent ->
                    navController.navigate(DestinasiDetailEvent.route.replace("{id_event}", idEvent))
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // Entry Peserta Screen
        composable(DestinasiInsertEvent.route) {
            EntryEventScreen(
                navigateBack = {
                    navController.popBackStack(DestinasiHomeEvent.route, inclusive = false)
                }
            )
        }

        // Detail Peserta Screen
        composable(
            route = DestinasiDetailEvent.route,
            arguments = listOf(navArgument(DestinasiDetailEvent.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getString(DestinasiDetailEvent.idArg) ?: ""
            DetailEventScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id ->
                    navController.navigate(DestinasiEditEvent.createRoute(id))
                },
                id = idEvent
            )
        }

        // Update Peserta Screen
        composable(
            route = DestinasiEditEvent.route,
            arguments = listOf(navArgument(DestinasiEditEvent.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getString(DestinasiEditEvent.idArg) ?: ""
            UpdateEventView(
                idEvent = idEvent,
                navigateBack = { navController.navigateUp() }
            )
        }

        //Tiket
        composable(DestinasiHomeTiket.route) {
            HomeTiketScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertTiket.route) },
                onDetailClick = { idTiket ->
                    navController.navigate(DestinasiDetailTiket.route.replace("{id_tiket}", idTiket))
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DestinasiDetailTiket.route,
            arguments = listOf(navArgument(DestinasiDetailTiket.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTiket = backStackEntry.arguments?.getString(DestinasiDetailTiket.idArg) ?: ""
            DetailTiketScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id ->
                    navController.navigate(DestinasiEditTiket.createRoute(id))
                },
                id = idTiket
            )
        }

        composable(
            route = DestinasiEditTiket.route,
            arguments = listOf(navArgument(DestinasiEditTiket.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTiket = backStackEntry.arguments?.getString(DestinasiEditTiket.idArg) ?: ""
            UpdateTiketScreen(
                idTiket = idTiket,
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(DestinasiInsertTiket.route) {
            EntryTiketScreen(
                navigateBack = {
                    navController.popBackStack(DestinasiHomeTiket.route, inclusive = false)
                }
            )
        }

        //Transaksi
        composable(DestinasiHomeTransaksi.route) {
            HomeTransaksiScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertTransaksi.route) },
                onDetailClick = { idTransaksi ->
                    navController.navigate(DestinasiDetailTransaksi.route.replace("{id_transaksi}", idTransaksi))
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiInsertTransaksi.route) {
            InsertTransaksiView(
                navigateBack = {
                    navController.popBackStack(DestinasiHomeTransaksi.route, inclusive = false)
                }
            )
        }

        composable(
            route = DestinasiDetailTransaksi.route,
            arguments = listOf(navArgument(DestinasiDetailTransaksi.idArg) { type = NavType.StringType })
        ) { backStackEntry ->
            val idTransaksi = backStackEntry.arguments?.getString(DestinasiDetailTransaksi.idArg) ?: ""
            DetailTransaksiScreen(
                navigateBack = { navController.popBackStack() },
                id = idTransaksi
            )
        }
    }
}

