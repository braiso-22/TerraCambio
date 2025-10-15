package com.braiso_22.terracambio.navigation

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.braiso_22.terracambio.chat.presentation.ChatPanel
import com.braiso_22.terracambio.identification.presentation.LoginPanel
import com.braiso_22.terracambio.listing.presentation.mapListings.MapListingItem
import com.braiso_22.terracambio.listing.presentation.mapListings.MapPanel
import com.braiso_22.terracambio.listing.presentation.myListings.MyListingsPanel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.NewListingPanel
import com.braiso_22.terracambio.listing.presentation.newListingPanel.NewListingUiEvent
import com.github.braiso_22.listing.vo.Location
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

    @Serializable
    data object Login : Screen
}

val startDestination: Screen = Screen.Login

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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navigateTo: (Screen) -> Unit = { screen ->
        if (selectedDestination != screen) {
            navController.navigate(route = screen)
            scope.launch {
                drawerState.close()
            }
        }
    }

    val useScaffold = selectedDestination != Screen.Login && selectedDestination != Screen.Chat

    if (useScaffold) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = (selectedDestination != Screen.AllListings
                    && selectedDestination != Screen.MyListings) || drawerState.isOpen,
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
                                    modifier = Modifier
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
                                    else -> {
                                        ""
                                    }
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
            ) { paddingValues ->
                AppNavHost(
                    navController = navController,
                    onNavigate = navigateTo,
                    modifier = modifier.padding(paddingValues),
                    snackbarHostState = snackbarHostState
                )
            }
        }
    } else {
        AppNavHost(
            navController = navController,
            onNavigate = navigateTo,
            modifier = modifier,
            snackbarHostState = snackbarHostState
        )
    }
}


@OptIn(ExperimentalUuidApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Screen.MyListings> {
            MyListingsPanel(
                modifier = Modifier.padding(8.dp)
            )
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
                            onNavigate(Screen.AllListings)
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
                originPlace = Location.example,
                onClickMap = {},
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
            ChatPanel(
                onClickBack = {
                    // custom because it has its own scaffold
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.Profile> {

        }
        composable<Screen.Login> {
            LoginPanel(
                onLogin = {
                    // custom because it has its own scaffold and we cant go back to Login after it working
                    navController.navigate(Screen.Profile) {
                        popUpTo(Screen.Login) {
                            inclusive = true
                        }
                    }
                }
            )
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