package com.braiso_22.terracambio.listing.application.port.out.dtos

import com.github.braiso_22.listing.domain.vo.CadastralCode
import com.github.braiso_22.listing.domain.vo.ListingName
import com.github.braiso_22.listing.domain.vo.ListingTransactions
import com.github.braiso_22.listing.domain.vo.OwnerId

data class AddListingServerDto(
    val name: ListingName,
    val listingTransactions: ListingTransactions,
    val cadastralCode: CadastralCode,
    val ownerId: OwnerId,
)