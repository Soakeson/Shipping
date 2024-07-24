package Server.Views

import Server.Observers.ShipmentTracker
import Server.Updates.UpdateRecord
import ShippingController
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class TrackerView() : View {
    @Composable
    @Preview
    override fun render() {
        val trackers = remember { mutableStateListOf<ShipmentTracker>() }
        var shippingId by remember { mutableStateOf(TextFieldValue("")) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BasicTextField(
                value = shippingId,
                onValueChange = { shippingId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colors.primary)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (shippingId.text.isNotBlank()) {
                        ShippingController.trackShipment(shippingId.text)?.let { trackers.add(it) }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Track")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .scrollable(rememberScrollState(), Orientation.Vertical)
            ) {
                for (tracker in trackers) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { trackers.remove(tracker) },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Text(text = "X")
                            }
                        }
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "ID: ${tracker.id.value}", style = MaterialTheme.typography.h5)
                            Divider()
                            Text(text = "Status: ${tracker.status.value}", style = MaterialTheme.typography.h6)
                            Text(text = "Location: ${tracker.location.value}", style = MaterialTheme.typography.h6)
                            Text(text = "Expected: ${tracker.expected.value}", style = MaterialTheme.typography.h6)
                            Text(text = "\nNotes", style = MaterialTheme.typography.h6)
                            Divider()
                            tracker.notes.forEachIndexed {index: Int, note: String ->
                                Text(text = "$index. $note", style = MaterialTheme.typography.body1)
                            }
                            Text(text = "\nUpdates", style = MaterialTheme.typography.h6)
                            Divider()
                            tracker.updates.forEachIndexed{ index: Int, update: UpdateRecord ->
                                Text(text = "$index. ${update.curr} at ${update.time}", style = MaterialTheme.typography.body1)
                            }
                        }
                    }
                    }
                }
            }
        }
    }
