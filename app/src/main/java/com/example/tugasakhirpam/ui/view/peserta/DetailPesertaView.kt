package com.example.tugasakhirpam.ui.view.peserta


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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.R
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.navigation.DestinasiDetailPeserta
import com.example.tugasakhirpam.ui.viewmodel.peserta.DetailPesertaViewModel
import com.example.tugasakhirpam.ui.viewmodel.peserta.DetailPesertaUiState
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.peserta.HomePesertaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPesertaScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    id: String,
    viewModel: DetailPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(id) {
        viewModel.getPesertaById(id)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPeserta.titleRes,
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
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Peserta")
            }
        }
    ) { innerPadding ->
        DetailPesertaStatus(
            detailUiState = viewModel.pesertaDetailUiState,
            retryAction = { viewModel.getPesertaById(id) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        )
    }
}

@Composable
private fun DetailPesertaStatus(
    detailUiState: DetailPesertaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailPesertaUiState.Loading -> OnLoadingDetail(modifier = modifier)
        is DetailPesertaUiState.Success -> DetailPesertaLayout(
            peserta = detailUiState.peserta,
            modifier = modifier
        )
        is DetailPesertaUiState.Error -> OnErrorDetail(retryAction, modifier = modifier)
    }
}

@Composable
private fun OnLoadingDetail(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
private fun OnErrorDetail(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.connectioneror),
            contentDescription = stringResource(R.string.loading_failed)
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun DetailPesertaLayout(
    peserta: Peserta,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(), // Menyesuaikan lebar dengan layar
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth() // Menyesuaikan lebar kolom dengan layar
                .wrapContentHeight(), // Menyesuaikan tinggi konten dengan ukuran teks
            verticalArrangement = Arrangement.spacedBy(12.dp) // Menjaga jarak antar elemen agar tidak terlalu rapat
        ) {
            // Menampilkan data ID Peserta
            Text(
                text = "ID Peserta: ${peserta.idPeserta}",
                style = MaterialTheme.typography.titleMedium, // Menggunakan style yang lebih kecil untuk menghemat ruang
                modifier = Modifier.fillMaxWidth()
            )
            // Menampilkan nama Peserta
            Text(
                text = "Nama Peserta: ${peserta.namaPeserta}",
                style = MaterialTheme.typography.titleMedium, // Menggunakan style yang lebih kecil untuk menghemat ruang
                modifier = Modifier.fillMaxWidth()
            )
            // Menampilkan email Peserta
            Text(
                text = "Email: ${peserta.email}",
                style = MaterialTheme.typography.titleMedium, // Menggunakan style yang lebih kecil untuk menghemat ruang
                modifier = Modifier.fillMaxWidth()
            )
            // Menampilkan nomor telepon Peserta
            Text(
                text = "No Telepon: ${peserta.nomorTelepon}",
                style = MaterialTheme.typography.titleMedium, // Menggunakan style yang lebih kecil untuk menghemat ruang
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


