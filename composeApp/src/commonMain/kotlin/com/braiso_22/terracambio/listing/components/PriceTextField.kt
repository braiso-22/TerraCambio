package com.braiso_22.terracambio.listing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.Res
import terracambio.composeapp.generated.resources.cant_be_empty
import terracambio.composeapp.generated.resources.price_in_euros

@Composable
fun PriceTextField(
    value: String,
    invalidValue: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val supportingText: (@Composable () -> Unit)? = if (invalidValue) {
        { Text(stringResource(Res.string.cant_be_empty)) }
    } else {
        null
    }


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Money,
                contentDescription = null
            )
        },
        isError = invalidValue,
        label = {
            Text(stringResource(Res.string.price_in_euros))
        },
        singleLine = true,
        supportingText = supportingText,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Preview
@Composable
private fun PriceTextFieldCompPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                PriceTextField(
                    value = "12",
                    invalidValue = true,
                    onValueChange = {},
                    Modifier.fillMaxWidth().padding(8.dp)
                )
            }
        }
    }
}