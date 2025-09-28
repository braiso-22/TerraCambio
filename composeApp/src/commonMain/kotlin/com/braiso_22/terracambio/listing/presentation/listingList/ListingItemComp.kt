package com.braiso_22.terracambio.listing.presentation.listingList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.braiso_22.listing.vo.TransactionType
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ListingItemComp(
    listing: ListingItem,
    onClick: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = if (listing.isSelected) {
            CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        } else {
            CardDefaults.outlinedCardColors()
        },
        border = BorderStroke(
            1.dp, if (listing.isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        onClick = { onClick(listing.id) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = listing.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(Icons.Default.Edit, null)
                }
            }
            Text(
                text = listing.location,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listing.transactions.values.toList()) { transaction ->
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = when (transaction) {
                                    is TransactionType.Buy -> {
                                        "Buy: ${transaction.value.toDecimal()}"
                                    }

                                    is TransactionType.Rent -> {
                                        "Rent: ${transaction.value.toDecimal()}"
                                    }

                                    TransactionType.Switch -> {
                                        "Switch"
                                    }
                                },
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
fun ListingItemCompPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                ListingItemComp(
                    onClick = {},
                    listing = ListingItem.example(0),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                ListingItemComp(
                    onClick = {},
                    listing = ListingItem.example(31),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }
        }
    }
}


