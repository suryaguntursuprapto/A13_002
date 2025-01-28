package com.example.tugasakhirpam.ui.view.transaksi

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.navigation.DestinasiInsertTransaksi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.transaksi.InsertTransaksi
import com.example.tugasakhirpam.ui.viewmodel.transaksi.InsertTransaksiViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTransaksiView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val transaksiUiState = viewModel.uiState
    val daftarTiket = viewModel.daftarTiket
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertTransaksi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InsertTransaksiBody(
            transaksi = transaksiUiState.insertTransaksi,
            onTransaksiValueChange = { viewModel.updateInsertTransaksiState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTransaksi()
                    navigateBack()
                }
            },
            daftarTiket = daftarTiket, // Pass the fetched list here
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )

    }
}

@Composable
fun InsertTransaksiBody(
    transaksi: InsertTransaksi,
    daftarTiket: List<Tiket>,
    onTransaksiValueChange: (InsertTransaksi) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTransaksi(
            transaksi = transaksi,
            onValueChange = onTransaksiValueChange,
            modifier = Modifier.fillMaxWidth(),
            daftarTiket = daftarTiket
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
fun FormInputTransaksi(
    transaksi: InsertTransaksi,
    onValueChange: (InsertTransaksi) -> Unit,
    modifier: Modifier = Modifier,
    daftarTiket: List<Tiket> // List of available tickets
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        var selectedDate by remember { mutableStateOf(transaksi.tanggalTransaksi) }
        val context = LocalContext.current

        // Handle the date picker dialog when needed
        val showDatePicker = remember { mutableStateOf(false) }

        if (showDatePicker.value) {
            // Show DatePicker dialog
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(year, month, dayOfMonth)
                    val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
                    selectedDate = formattedDate
                    onValueChange(transaksi.copy(tanggalTransaksi = formattedDate))
                    showDatePicker.value = false // Close the dialog
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            LaunchedEffect(true) {
                datePickerDialog.show()
            }
        }

        // ID Transaksi
        OutlinedTextField(
            value = transaksi.idTransaksi,
            onValueChange = { onValueChange(transaksi.copy(idTransaksi = it)) },
            label = { Text("ID Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        // Dropdown for ID Tiket
        var expandedTiket by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandedTiket,
            onExpandedChange = { expandedTiket = !expandedTiket }
        ) {
            OutlinedTextField(
                value = daftarTiket.find { it.idTiket == transaksi.idTiket }?.idPengguna ?: "",
                onValueChange = {},
                label = { Text("ID Tiket") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedTiket) }
            )
            ExposedDropdownMenu(
                expanded = expandedTiket,
                onDismissRequest = { expandedTiket = false }
            ) {
                daftarTiket.forEach { tiket ->
                    DropdownMenuItem(
                        text = { Text(tiket.idPengguna) },
                        onClick = {
                            // Save the selected ID Tiket, even though the name is displayed
                            onValueChange(transaksi.copy(idTiket = tiket.idTiket))
                            expandedTiket = false
                        }
                    )
                }
            }
        }

        // Jumlah Tiket
        OutlinedTextField(
            value = transaksi.jumlahTiket,
            onValueChange = { onValueChange(transaksi.copy(jumlahTiket = it)) },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Jumlah Pembayaran
        OutlinedTextField(
            value = transaksi.jumlahPembayaran,
            onValueChange = { onValueChange(transaksi.copy(jumlahPembayaran = it)) },
            label = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = selectedDate,
            onValueChange = { newDate ->
                selectedDate = newDate
                onValueChange(transaksi.copy(tanggalTransaksi = selectedDate))
            },
            label = { Text("Tanggal Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker.value = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            }
        )
    }
}

@Composable
private fun showDatePickerDialog(onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
