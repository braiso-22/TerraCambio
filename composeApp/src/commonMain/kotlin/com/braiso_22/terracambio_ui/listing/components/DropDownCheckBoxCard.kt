package com.braiso_22.terracambio_ui.listing.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DropDownCheckBoxCard(
    text: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    dropDownContent: @Composable () -> Unit = {},
) {
    OutlinedCard(
        onClick = { onChecked(!checked) },
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onChecked
            )
            Text(text)
        }
        if (checked) {
            dropDownContent()
        }
    }
}

@Preview
@Composable
private fun DropDownCheckBoxCardCompPreview() {
    var checked by remember { mutableStateOf(true) }

    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                DropDownCheckBoxCard(
                    text = "pro",
                    checked = checked,
                    onChecked = { checked = !checked },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text("Test", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}