package com.braiso_22.terracambio.listing.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.braiso_22.terracambio.listing.presentation.mapListings.MapListingItem
import com.braiso_22.terracambio.listing.presentation.mapListings.MapPanel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.NewListingPanel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.NewListingUiEvent
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import terracambio.composeapp.generated.resources.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@Serializable
sealed interface Screen {
    @Serializable
    data object MyListings : Screen

    @Serializable
    data object NewListing : Screen

    @Serializable
    data object AllListings : Screen

    @Serializable
    data object Chat : Screen

    @Serializable
    data object Profile : Screen
}

val startDestination: Screen = Screen.Chat

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    // Observe the back stack entry to get the current screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination: Screen = remember(navBackStackEntry) {
        // List of all possible screens
        val screens = listOf(
            Screen.MyListings,
            Screen.NewListing,
            Screen.AllListings,
            Screen.Chat,
            Screen.Profile
        )

        // Find the screen object whose class name matches the current route
        screens.find { screen ->
            navBackStackEntry?.destination?.route == screen::class.qualifiedName
        } ?: startDestination // Fallback to the start destination if no match is found
    }
    val snackbarHostState = remember { SnackbarHostState() }

    fun navigateTo(screen: Screen) {
        if (selectedDestination == screen) return
        navController.navigate(route = screen)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = selectedDestination != Screen.AllListings || drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.my_listings)) },
                    selected = selectedDestination == Screen.MyListings,
                    onClick = { navigateTo(Screen.MyListings) },
                    icon = { Icon(Icons.Default.MyLocation, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.new_listing)) },
                    selected = selectedDestination == Screen.NewListing,
                    onClick = { navigateTo(Screen.NewListing) },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.listing_map)) },
                    selected = selectedDestination == Screen.AllListings,
                    onClick = { navigateTo(Screen.AllListings) },
                    icon = { Icon(Icons.Default.Map, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.chat)) },
                    selected = selectedDestination == Screen.Chat,
                    onClick = { navigateTo(Screen.Chat) },
                    icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(stringResource(Res.string.profile)) },
                    selected = selectedDestination == Screen.Profile,
                    onClick = { navigateTo(Screen.Profile) },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    },
                    title = {
                        Text(
                            when (selectedDestination) {
                                Screen.NewListing -> stringResource(Res.string.new_listing)
                                Screen.AllListings -> stringResource(Res.string.listing_map)
                                Screen.Chat -> stringResource(Res.string.chat)
                                Screen.MyListings -> stringResource(Res.string.my_listings)
                                Screen.Profile -> stringResource(Res.string.profile)
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            bottomBar = {
                if (selectedDestination != Screen.AllListings && selectedDestination != Screen.Chat) {
                    return@Scaffold
                }
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    NavigationBarItem(
                        selected = selectedDestination == Screen.Chat,
                        onClick = {
                            navigateTo(Screen.Chat)
                        },
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Filled.Chat,
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(Res.string.chat)) }
                    )
                    NavigationBarItem(
                        selected = selectedDestination == Screen.AllListings,
                        onClick = {
                            navigateTo(Screen.AllListings)
                        },
                        icon = {
                            Icon(
                                Icons.Default.Map,
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(Res.string.listing_map)) }
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                startDestination = startDestination
            ) {
                composable<Screen.MyListings> {
                    // TODO: MyListings
                }
                composable<Screen.NewListing> {
                    val badName = stringResource(Res.string.bad_name)
                    val badCadastralCode = stringResource(Res.string.bad_cadastral_code)
                    val badTransactions = stringResource(Res.string.bad_transactions)
                    val cadastralCodeNotFound = stringResource(Res.string.cadastral_code_not_found)
                    val couldNotCreateListing = stringResource(Res.string.could_not_create)
                    val listingCreated = stringResource(Res.string.listing_created)
                    NewListingPanel(
                        onEvent = { event ->
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                if (event == NewListingUiEvent.ListingCreated) {
                                    navigateTo(Screen.AllListings)
                                } else {
                                    snackbarHostState.showSnackbar(
                                        when (event) {
                                            NewListingUiEvent.CadastralCodeNotFound -> cadastralCodeNotFound
                                            NewListingUiEvent.CouldNotCreate -> couldNotCreateListing
                                            NewListingUiEvent.ListingCreated -> listingCreated
                                            NewListingUiEvent.BadName -> badName
                                            NewListingUiEvent.InvalidCadastralCode -> badCadastralCode
                                            NewListingUiEvent.InvalidTransactions -> badTransactions
                                        }
                                    )
                                }
                            }
                        }, modifier = Modifier.padding(8.dp)
                    )
                }
                composable<Screen.AllListings> {
                    MapPanel(
                        listings = List(10) {
                            MapListingItem(
                                id = Uuid.random(),
                                title = "Listing $it",
                                address = "Address $it",
                                latitude = 42.4575400841553 - it.toDouble() / 100,
                                longitude = it.toDouble() / 100 - 8.32239179787197
                            )
                        },
                        onClickListing = {},
                        modifier = Modifier.padding(8.dp)
                    )
                }
                composable<Screen.Chat> {
                    // TODO: Chat screen
                }
                composable<Screen.Profile> {

                }
            }
        }
    }
}


@Preview
@Composable
fun NavigationPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Navigation()
            }
        }
    }
}