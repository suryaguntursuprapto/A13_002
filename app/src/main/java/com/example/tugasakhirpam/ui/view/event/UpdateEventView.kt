import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpam.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhirpam.ui.viewmodel.event.UpdateEventViewModel
import com.example.tugasakhirpam.ui.viewmodel.event.UpdateEventUiState
import com.example.tugasakhirpam.ui.viewmodel.event.UpdateEventEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEventView(
    idEvent: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            viewModel.getEventById(idEvent)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text(text = "Update Event") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = navigateBack) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        UpdateEventBody(
            updateEventUiState = viewModel.uiState,
            onEventValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEvent(idEvent)
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .fillMaxSize()
        )
    }
}

@Composable
fun UpdateEventBody(
    updateEventUiState: UpdateEventUiState,
    onEventValueChange: (UpdateEventEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        UpdateEventForm(
            updateEventEvent = updateEventUiState.updateEventEvent,
            onValueChange = onEventValueChange,
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
fun UpdateEventForm(
    updateEventEvent: UpdateEventEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateEventEvent) -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf(updateEventEvent.tanggalEvent) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateEventEvent.namaEvent,
            onValueChange = { onValueChange(updateEventEvent.copy(namaEvent = it)) },
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = updateEventEvent.idEvent,
            onValueChange = { onValueChange(updateEventEvent.copy(idEvent = it)) },
            label = { Text("ID Event") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = updateEventEvent.deskripsiEvent,
            onValueChange = { onValueChange(updateEventEvent.copy(deskripsiEvent = it)) },
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = selectedDate,
            onValueChange = { newDate ->
                selectedDate = newDate
                onValueChange(updateEventEvent.copy(tanggalEvent = selectedDate))
            },
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    // Show DatePicker Dialog when clicked
                    coroutineScope.launch {
                        showDatePickerDialog(context) { date ->
                            val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
                            selectedDate = formattedDate
                            onValueChange(updateEventEvent.copy(tanggalEvent = formattedDate))
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            }
        )
    }
}

private fun showDatePickerDialog(context: Context, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
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
