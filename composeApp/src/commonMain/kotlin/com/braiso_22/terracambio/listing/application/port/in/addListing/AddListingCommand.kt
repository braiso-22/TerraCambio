package com.braiso_22.terracambio.listing.application.port.`in`.addListing

import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions


data class AddListingCommand(
    val listingName: ListingName,
    val transactions: ListingTransactions,
    val cadastralCode: CadastralCode,
)