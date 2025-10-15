package com.braiso_22.terracambio.identification.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginPanel(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("") }
    val onChangeEmail: (String) -> Unit = { email = it }
    val snackbarHostState = remember { SnackbarHostState() }
    val corotineScope = rememberCoroutineScope()

    val onLogin: () -> Unit = {
        if (email.isNotEmpty()) {
            email = ""
            onLogin()
        } else {
            corotineScope.launch {
                snackbarHostState.showSnackbar(
                    "Bad email"
                )
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            LoginContent(
                email = email,
                onChangeEmail = onChangeEmail,
                onLogin = onLogin,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
fun LoginContent(
    email: String,
    onChangeEmail: (String) -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = onChangeEmail,
                singleLine = true,
                placeholder = {
                    Text("Your email here")
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.padding(16.dp))
            Button(onLogin, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
        }
    }
}


@Preview
@Composable
fun LoginContentPreview() {
    MaterialTheme {
        Scaffold { paddingValues ->
            LoginPanel(
                onLogin = {},
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            )
        }
    }
}


