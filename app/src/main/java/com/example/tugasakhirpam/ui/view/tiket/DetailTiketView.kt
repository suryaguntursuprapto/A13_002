package com.example.tugasakhirpam.ui.view.tiket

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
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.navigation.DestinasiDetailTiket
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.tiket.DetailTiketUiState
import com.example.tugasakhirpam.ui.viewmodel.tiket.DetailTiketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTiketScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    id: String,
    viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(id) {
        viewModel.getTiketById(id)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTiket.titleRes,
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
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Tiket")
            }
        }
    ) { innerPadding ->
        DetailTiketStatus(
            detailUiState = viewModel.tiketDetailUiState,
            retryAction = { viewModel.getTiketById(id) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        )
    }
}

@Composable
private fun DetailTiketStatus(
    detailUiState: DetailTiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailTiketUiState.Loading -> OnLoadingTiket(modifier = modifier)
        is DetailTiketUiState.Success -> DetailTiketLayout(
            tiket = detailUiState.tiket,
            modifier = modifier
        )
        is DetailTiketUiState.Error -> OnErrorTiket(retryAction, modifier = modifier)
    }
}

@Composable
private fun OnLoadingTiket(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
private fun OnErrorTiket(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
            text = "Failed to load ticket details.",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun DetailTiketLayout(
    tiket: Tiket,
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
                text = "ID Tiket: ${tiket.idTiket}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Peserta: ${tiket.idPengguna}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Event: ${tiket.idEvent}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Kapasitas Tiket: ${tiket.kapasitasTiket}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Harga Tiket: ${tiket.hargaTiket}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
