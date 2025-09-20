package com.braiso_22.terracambio.listing.infrastructure.adapters.input.springbootApi

import com.braiso_22.terracambio.listing.application.input.addListing.AddListing
import com.braiso_22.terracambio.listing.application.input.addListing.AddListingCommand
import com.braiso_22.terracambio.listing.application.input.addListing.AddListingResult
import com.braiso_22.terracambio.listing.application.input.validateCadastralCode.ValidateCadastralCodeCommand
import com.braiso_22.terracambio.listing.application.input.validateCadastralCode.ValidateCadastralCodeResult
import com.braiso_22.terracambio.listing.application.input.getListings.GetListings
import com.braiso_22.terracambio.listing.application.input.validateCadastralCode.ValidateCadastralCode
import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.ListingName
import com.github.braiso_22.listing.vo.ListingTransactions
import com.github.braiso_22.listing.vo.OwnerId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@RestController
@RequestMapping("/api/v1/listing")
class ListingController(
    private val getListings: GetListings,
    private val addListing: AddListing,
    private val validateCadastralCode: ValidateCadastralCode,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalUuidApi::class)
    @GetMapping
    suspend fun getAllListings(): ResponseEntity<List<ListingDto>> {
        val listings = getListings()

        val dtoList = listings.map { it.toDto() }

        return ResponseEntity.ok(dtoList)
    }

    @OptIn(ExperimentalUuidApi::class)
    @GetMapping("validate-cadastral-code/{code}")
    suspend fun validateCadastralCode(@PathVariable code: String): ResponseEntity<Any> {

        val command = try{
            ValidateCadastralCodeCommand(CadastralCode(code))
        } catch (e: IllegalArgumentException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }

        val codeResult = validateCadastralCode(command)

        return when (codeResult) {
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

        logger.info("Received request to add a new listing {}", dto)

        val command = try {
            AddListingCommand(
                ownerId = OwnerId(Uuid.parse(dto.ownerId)),
                listingName = ListingName(dto.listingName),
                transactions = ListingTransactions(dto.transactions.toDomain()),
                cadastralCode = CadastralCode(dto.cadastralCode),
            )
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }

        val result: AddListingResult = addListing(command)

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
