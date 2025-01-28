package com.example.tugasakhirpam.ui.view.tiket

import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.ui.viewmodel.tiket.UpdateTiketViewModel
import com.example.tugasakhirpam.ui.viewmodel.tiket.UpdateTiketEvent
import com.example.tugasakhirpam.ui.viewmodel.tiket.UpdateTiketUiState
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketScreen(
    idTiket: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val daftarPeserta = viewModel.daftarPeserta
    val daftarEvent = viewModel.daftarEvent
    // Fetch ticket data when the view is first created
    LaunchedEffect(key1 = idTiket) {
        coroutineScope.launch {
            viewModel.getTiketById(idTiket)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "Update Tiket") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        UpdateTiketBody(
            updateTiketUiState = viewModel.uiState,
            onEventValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket(idTiket)
                    navigateBack()
                }
            },
            daftarPeserta = daftarPeserta,  // Pass daftarPeserta here
            daftarEvent = daftarEvent,      // Pass daftarEvent here
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .fillMaxSize()
        )
    }
}



@Composable
fun UpdateTiketBody(
    updateTiketUiState: UpdateTiketUiState,
    daftarPeserta: List<Peserta>,
    daftarEvent: List<Event>,
    onEventValueChange: (UpdateTiketEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        UpdateTiketForm(
            updateTiketEvent = updateTiketUiState.updateTiketEvent,
            onValueChange = onEventValueChange,
            daftarPeserta = daftarPeserta, // Pass the list of participants
            daftarEvent = daftarEvent,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Perubahan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketForm(
    updateTiketEvent: UpdateTiketEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateTiketEvent) -> Unit = {},
    daftarPeserta: List<Peserta>, // Accept daftarPeserta here
    daftarEvent: List<Event>      // Accept daftarEvent here
) {
    // States to manage dropdown visibility
    var expandedPeserta by remember { mutableStateOf(false) }
    var expandedEvent by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateTiketEvent.idTiket,
            onValueChange = { onValueChange(updateTiketEvent.copy(idTiket = it)) },
            label = { Text("ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Dropdown for Peserta
        ExposedDropdownMenuBox(
            expanded = expandedPeserta,
            onExpandedChange = { expandedPeserta = !expandedPeserta }
        ) {
            OutlinedTextField(
                value = daftarPeserta.find { it.idPeserta == updateTiketEvent.idPengguna }?.namaPeserta ?: "",
                onValueChange = {},
                label = { Text("Peserta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedPeserta) }
            )
            ExposedDropdownMenu(
                expanded = expandedPeserta,
                onDismissRequest = { expandedPeserta = false }
            ) {
                daftarPeserta.forEach { peserta ->
                    DropdownMenuItem(
                        text = { Text(peserta.namaPeserta) },
                        onClick = {
                            // Save the ID of the selected user
                            onValueChange(updateTiketEvent.copy(idPengguna = peserta.idPeserta))
                            expandedPeserta = false
                        }
                    )
                }
            }
        }

        // Dropdown for Event
        ExposedDropdownMenuBox(
            expanded = expandedEvent,
            onExpandedChange = { expandedEvent = !expandedEvent }
        ) {
            OutlinedTextField(
                value = daftarEvent.find { it.idEvent == updateTiketEvent.idEvent }?.namaEvent ?: "",
                onValueChange = {},
                label = { Text("Event") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedEvent) }
            )
            ExposedDropdownMenu(
                expanded = expandedEvent,
                onDismissRequest = { expandedEvent = false }
            ) {
                daftarEvent.forEach { event ->
                    DropdownMenuItem(
                        text = { Text(event.namaEvent) },
                        onClick = {
                            // Save the ID of the selected event
                            onValueChange(updateTiketEvent.copy(idEvent = event.idEvent))
                            expandedEvent = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = updateTiketEvent.kapasitasTiket,
            onValueChange = { onValueChange(updateTiketEvent.copy(kapasitasTiket = it)) },
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        OutlinedTextField(
            value = updateTiketEvent.hargaTiket,
            onValueChange = { onValueChange(updateTiketEvent.copy(hargaTiket = it)) },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
    }
}

