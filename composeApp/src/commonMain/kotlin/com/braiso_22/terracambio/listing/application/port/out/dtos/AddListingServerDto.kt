package com.braiso_22.terracambio.listing.application.port.out.dtos

import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions
import com.github.braiso_22.listing.vo.OwnerId

data class AddListingServerDto(
    val name: ListingName,
    val listingTransactions: ListingTransactions,
    val cadastralCode: CadastralCode,
    val ownerId: OwnerId,
)