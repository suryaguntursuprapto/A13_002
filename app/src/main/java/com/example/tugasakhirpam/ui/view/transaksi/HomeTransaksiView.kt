package com.example.tugasakhirpam.ui.view.transaksi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.R
import com.example.tugasakhirpam.model.Transaksi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.transaksi.HomeTransaksiViewModel
import com.example.tugasakhirpam.ui.viewmodel.transaksi.HomeTransaksiUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTransaksiScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory )
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Daftar Transaksi",
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTransaksi()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaksi")
            }
        }
    ) { innerPadding ->
        HomeTransaksiStatus(
            homeUiState = viewModel.transaksiUiState,
            retryAction = { viewModel.getTransaksi() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                viewModel.deleteTransaksi(it.idTransaksi)
                viewModel.getTransaksi()
            },
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeTransaksiStatus(
    homeUiState: HomeTransaksiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {},
    onDetailClick: (String) -> Unit = {}
) {
    when (homeUiState) {
        is HomeTransaksiUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomeTransaksiUiState.Success -> {
            if (homeUiState.transaksi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Transaksi")
                }
            } else {
                TransaksiLayout(
                    transaksi = homeUiState.transaksi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idTransaksi) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeTransaksiUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun TransaksiLayout(
    transaksi: List<Transaksi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Transaksi) -> Unit,
    onDeleteClick: (Transaksi) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transaksi) { item ->
            TransaksiCard(
                transaksi = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun TransaksiCard(
    transaksi: Transaksi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transaksi) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transaksi.idTransaksi,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(transaksi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = transaksi.idTiket,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Text(
                text = "Jumlah Tiket: ${transaksi.jumlahTiket}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Pembayaran: Rp ${transaksi.jumlahPembayaran}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tanggal: ${transaksi.tanggalTransaksi}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = "Loading..."
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connectioneror), contentDescription = null
        )
        Text(text = "Gagal Memuat Data", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}
