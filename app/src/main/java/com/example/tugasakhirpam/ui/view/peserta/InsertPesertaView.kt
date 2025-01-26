package com.example.tugasakhirpam.ui.view.peserta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.navigation.DestinasiInsertPeserta
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.peserta.InsertPesertaViewModel
import com.example.tugasakhirpam.ui.viewmodel.peserta.InsertPesertaUiState
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.peserta.InsertPesertaFormErrors
import com.example.tugasakhirpam.ui.viewmodel.peserta.toInsertPesertaEvent
import com.example.tugasakhirpam.ui.viewmodel.peserta.toPeserta
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPesertaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPeserta.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertPesertaUiState = viewModel.uiState,
            onPesertaValueChange = { peserta ->
                // Ensure to pass Peserta directly to the onPesertaValueChange
                viewModel.updateInsertPesertaState(peserta.toInsertPesertaEvent())
            },
            onSaveClick = {
                coroutineScope.launch {
                    // Call validation and check if it's valid
                    if (viewModel.validateInsertPesertaForm()) {
                        viewModel.insertPeserta()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertPesertaUiState: InsertPesertaUiState,
    onPesertaValueChange: (Peserta) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            peserta = insertPesertaUiState.insertPesertaEvent.toPeserta(), // Ensure it's Peserta
            onValueChange = onPesertaValueChange,
            validationErrors = InsertPesertaFormErrors(), // Pass validation errors
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onValueChange: (Peserta) -> Unit = {},
    enabled: Boolean = true,
    validationErrors: InsertPesertaFormErrors? = null // Add validation errors parameter
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = peserta.namaPeserta,
            onValueChange = { onValueChange(peserta.copy(namaPeserta = it)) },
            label = { Text("Nama Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.namaPeserta != null // Show error state if validation fails
        )
        validationErrors?.namaPeserta?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = peserta.idPeserta,
            onValueChange = { onValueChange(peserta.copy(idPeserta = it)) },
            label = { Text("ID Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.idPeserta != null // Show error state if validation fails
        )
        validationErrors?.idPeserta?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = peserta.email,
            onValueChange = { onValueChange(peserta.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.email != null // Show error state if validation fails
        )
        validationErrors?.email?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = peserta.nomorTelepon,
            onValueChange = { onValueChange(peserta.copy(nomorTelepon = it)) },
            label = { Text("No Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.nomorTelepon != null // Show error state if validation fails
        )
        validationErrors?.nomorTelepon?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding((12.dp))
        )
    }
}
