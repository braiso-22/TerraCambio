package com.braiso_22.terracambio.listing.infrastructure.adapters.`in`.springboot_api

import com.github.braiso_22.listing.application.port.`in`.addListing.AddListing
import com.github.braiso_22.listing.application.port.`in`.addListing.AddListingCommand
import com.github.braiso_22.listing.application.port.`in`.addListing.AddListingResult
import com.github.braiso_22.listing.application.port.`in`.getListings.GetListings
import com.github.braiso_22.listing.application.port.`in`.validateCadastralCode.ValidateCadastralCode
import listing.application.port.`in`.validateCadastralCode.ValidateCadastralCodeCommand
import listing.application.port.`in`.validateCadastralCode.ValidateCadastralCodeResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.uuid.ExperimentalUuidApi

@RestController
@RequestMapping("/api/v1/listing")
class ListingController(
    private val getListings: GetListings,
    private val addListing: AddListing,
    private val validateCadastralCode: ValidateCadastralCode
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalUuidApi::class)
    @GetMapping
    suspend fun getAllListings(): ResponseEntity<List<ListingDto>> {
        val listings = getListings()

        val dtos = listings.map { it.toDto() }

        return ResponseEntity.ok(dtos)
    }

    @OptIn(ExperimentalUuidApi::class)
    @GetMapping("validate-cadastral-code/{code}")
    suspend fun validateCadastralCode(@PathVariable code: String): ResponseEntity<Any> {
        val codeResult = validateCadastralCode(ValidateCadastralCodeCommand(code))

        return when (codeResult) {
            is ValidateCadastralCodeResult.InvalidFormat -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(codeResult.message)
            }

            ValidateCadastralCodeResult.NotFound -> {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Code not found")
            }

            ValidateCadastralCodeResult.Valid -> {
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @PostMapping
    suspend fun addListing(@RequestBody dto: CreateListingDto): ResponseEntity<Any> {

        logger.info("Received request to add a new listing: $dto")

        val result: AddListingResult = addListing(
            AddListingCommand(
                ownerId = dto.ownerId,
                listingName = dto.listingName,
                types = dto.transactions.toCommand(),
                cadastralCode = dto.cadastralCode,
            )
        )

        return when (result) {
            is AddListingResult.Success -> {
                ResponseEntity.status(HttpStatus.CREATED).build()
            }

            is AddListingResult.CadastralCodeNotFound -> {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.message)
            }

            is AddListingResult.BadCommand -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.message)
            }
        }
    }
}