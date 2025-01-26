package com.example.tugasakhirpam.ui.view.event

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
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.navigation.DestinasiInsertEvent
import com.example.tugasakhirpam.navigation.DestinasiNavigasi
import com.example.tugasakhirpam.ui.customwidget.CostumeTopAppBar
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.event.InsertEventViewModel
import com.example.tugasakhirpam.ui.viewmodel.event.InsertEventUiState
import com.example.tugasakhirpam.ui.viewmodel.event.InsertEventFormErrors
import com.example.tugasakhirpam.ui.viewmodel.event.toEvent
import com.example.tugasakhirpam.ui.viewmodel.event.toInsertEvent
import kotlinx.coroutines.launch
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertEventUiState = viewModel.uiState,
            onEventValueChange = { event ->
                // Ensure to pass Event directly to the onEventValueChange
                viewModel.updateInsertEventState(event.toInsertEvent())
            },
            onSaveClick = {
                coroutineScope.launch {
                    // Call validation and check if it's valid
                    if (viewModel.validateInsertEventForm()) {
                        viewModel.insertEvent()
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
    insertEventUiState: InsertEventUiState,
    onEventValueChange: (Event) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            event = insertEventUiState.insertEvent.toEvent(), // Ensure it's Event
            onValueChange = onEventValueChange,
            validationErrors = InsertEventFormErrors(), // Pass validation errors
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
    event: Event,
    modifier: Modifier = Modifier,
    onValueChange: (Event) -> Unit = {},
    enabled: Boolean = true,
    validationErrors: InsertEventFormErrors? = null // Add validation errors parameter
) {
    var selectedDate by remember { mutableStateOf(event.tanggalEvent) }
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
                onValueChange(event.copy(tanggalEvent = formattedDate))
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

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = event.namaEvent,
            onValueChange = { onValueChange(event.copy(namaEvent = it)) },
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.namaEvent != null // Show error state if validation fails
        )
        validationErrors?.namaEvent?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = event.idEvent,
            onValueChange = { onValueChange(event.copy(idEvent = it)) },
            label = { Text("ID Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.idEvent != null
        )
        validationErrors?.idEvent?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = event.deskripsiEvent,
            onValueChange = { onValueChange(event.copy(deskripsiEvent = it)) },
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.deskripsiEvent != null
        )
        validationErrors?.deskripsiEvent?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = selectedDate,
            onValueChange = { newDate ->
                selectedDate = newDate
                onValueChange(event.copy(tanggalEvent = selectedDate))
            },
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = validationErrors?.tanggalEvent != null,
            trailingIcon = {
                IconButton(onClick = { showDatePicker.value = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            }
        )
        validationErrors?.tanggalEvent?.let {
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
            modifier = Modifier.padding(12.dp)
        )
    }
}

// DatePickerDialog functionality to show calendar and pick a date

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