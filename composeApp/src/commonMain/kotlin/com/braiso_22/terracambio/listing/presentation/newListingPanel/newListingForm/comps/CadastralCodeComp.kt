package com.braiso_22.terracambio.listing.presentation.newListingPanel.newListingForm.comps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.terracambio.listing.presentation.newListingPanel.CadastralCodeState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.*

@Composable
fun CadastralCodeComp(
    state: CadastralCodeState,
    onChangeCode: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.enter_cadastral_code),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(Res.string.where_to_find_cadastral_code),
            style = MaterialTheme.typography.bodyMedium,
        )
        OutlinedTextField(
            value = state.value,
            onValueChange = onChangeCode,
            isError = when (state) {
                is CadastralCodeState.InvalidFormat, is CadastralCodeState.NotFound -> true
                else -> false
            },
            label = {
                Text(text = stringResource(Res.string.cadastral_code))
            },
            singleLine = true,
            supportingText = {
                Text(
                    text = when (state) {
                        is CadastralCodeState.NotFound -> {
                            stringResource(Res.string.cadastral_code_not_found)
                        }

                        else -> {
                            stringResource(Res.string.cadastral_code_requirement)
                        }
                    }
                )
            },
            trailingIcon = {
                when (state) {
                    is CadastralCodeState.Pristine -> {
                        null
                    }

                    is CadastralCodeState.Loading -> {
                        CircularProgressIndicator(
                            color = LocalContentColor.current,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp),
                        )
                    }

                    is CadastralCodeState.Valid -> {
                        Icon(Icons.Default.Check, null)
                    }

                    is CadastralCodeState.InvalidFormat, is CadastralCodeState.NotFound -> {
                        Icon(Icons.Default.Error, null)
                    }

                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun CadastralCodeCompPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(it)
                    .fillMaxSize()
            ) {
                // Fake preview state and onEvent to preview behaviour
                CadastralCodeComp(
                    state = CadastralCodeState.Loading("asdasdasdasd"),
                    onChangeCode = {},
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


