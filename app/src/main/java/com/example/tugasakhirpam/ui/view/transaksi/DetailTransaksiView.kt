package com.example.tugasakhirpam.ui.view.transaksi

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.R
import com.example.tugasakhirpam.model.Transaksi
import com.example.tugasakhirpam.navigation.DestinasiDetailTransaksi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.transaksi.DetailTransaksiUiState
import com.example.tugasakhirpam.ui.viewmodel.transaksi.DetailTransaksiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaksiScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    id: String,
    viewModel: DetailTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(id) {
        viewModel.getTransaksiById(id)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTransaksi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEdit(id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Transaksi")
            }
        }
    ) { innerPadding ->
        DetailTransaksiStatus(
            detailUiState = viewModel.transaksiDetailUiState,
            retryAction = { viewModel.getTransaksiById(id) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        )
    }
}

@Composable
private fun DetailTransaksiStatus(
    detailUiState: DetailTransaksiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailTransaksiUiState.Loading -> OnLoadingTransaksi(modifier = modifier)
        is DetailTransaksiUiState.Success -> DetailTransaksiLayout(
            transaksi = detailUiState.transaksi,
            modifier = modifier
        )
        is DetailTransaksiUiState.Error -> OnErrorTransaksi(retryAction, modifier = modifier)
    }
}

@Composable
private fun OnLoadingTransaksi(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
private fun OnErrorTransaksi(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.connectioneror),
            contentDescription = "Error Loading Data"
        )
        Text(
            text = "Failed to load transaction details.",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun DetailTransaksiLayout(
    transaksi: Transaksi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "ID Transaksi: ${transaksi.idTransaksi}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "ID Tiket: ${transaksi.idTiket}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Jumlah Tiket: ${transaksi.jumlahTiket}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Pembayaran: Rp ${transaksi.jumlahPembayaran}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Tanggal: ${transaksi.tanggalTransaksi}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
