package com.braiso_22.terracambio.listing.application.input.addListing

import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions
import com.github.braiso_22.listing.vo.OwnerId

data class AddListingCommand(
    val ownerId: OwnerId,
    val listingName: ListingName,
    val transactions: ListingTransactions,
    val cadastralCode: CadastralCode,
)
