package com.braiso_22.terracambio.listing.application.port.`in`

import com.github.braiso_22.listing.domain.vo.CadastralCode
import com.github.braiso_22.listing.domain.vo.ListingName
import com.github.braiso_22.listing.domain.vo.ListingTransactions


data class AddListingCommand(
    val listingName: ListingName,
    val transactions: ListingTransactions,
    val cadastralCode: CadastralCode,
)