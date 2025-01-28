package com.example.tugasakhirpam.ui.view.tiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.navigation.DestinasiInsertEvent
import com.example.tugasakhirpam.navigation.DestinasiInsertTiket
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.tugasakhirpam.ui.viewmodel.tiket.InsertTiket
import kotlinx.coroutines.launch
import androidx.compose.material3.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val tiketUiState = viewModel.uiState
    val daftarPeserta = viewModel.daftarPeserta
    val daftarEvent = viewModel.daftarEvent
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            tiket = tiketUiState.insertTiket,
            daftarPeserta = daftarPeserta,
            daftarEvent = daftarEvent,
            onTiketValueChange = { viewModel.updateInsertTiketState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    tiket: InsertTiket,
    daftarPeserta: List<Peserta>,
    daftarEvent: List<Event>,
    onTiketValueChange: (InsertTiket) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTiket(
            tiket = tiket,
            daftarPeserta = daftarPeserta,
            daftarEvent = daftarEvent,
            onValueChange = onTiketValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputTiket(
    tiket: InsertTiket,
    daftarPeserta: List<Peserta>,
    daftarEvent: List<Event>,
    onValueChange: (InsertTiket) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {

        // Kapasitas Tiket
        OutlinedTextField(
            value = tiket.idTiket,
            onValueChange = { onValueChange(tiket.copy(idTiket = it)) },
            label = { Text("ID Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        // Dropdown for Peserta
        var expandedPeserta by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedPeserta,
            onExpandedChange = { expandedPeserta = !expandedPeserta }
        ) {
            OutlinedTextField(
                value = daftarPeserta.find { it.idPeserta == tiket.idPengguna }?.namaPeserta ?: "",
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
                            // Simpan ID peserta, meskipun nama yang ditampilkan
                            onValueChange(tiket.copy(idPengguna = peserta.idPeserta))
                            expandedPeserta = false
                        }
                    )
                }
            }
        }

        // Dropdown for Event
        var expandedEvent by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedEvent,
            onExpandedChange = { expandedEvent = !expandedEvent }
        ) {
            OutlinedTextField(
                value = daftarEvent.find { it.idEvent == tiket.idEvent }?.namaEvent ?: "",
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
                            // Simpan ID event, meskipun nama yang ditampilkan
                            onValueChange(tiket.copy(idEvent = event.idEvent))
                            expandedEvent = false
                        }
                    )
                }
            }
        }

        // Kapasitas Tiket
        OutlinedTextField(
            value = tiket.kapasitasTiket,
            onValueChange = { onValueChange(tiket.copy(kapasitasTiket = it)) },
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Harga Tiket
        OutlinedTextField(
            value = tiket.hargaTiket,
            onValueChange = { onValueChange(tiket.copy(hargaTiket = it)) },
            label = { Text("Harga Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
