package com.openclassrooms.api.controller;

import com.openclassrooms.api.exception.InvalidCredentialsException;
import com.openclassrooms.api.model.entity.Rental;
import com.openclassrooms.api.model.request.rentals.CreateRentalRequest;
import com.openclassrooms.api.model.request.rentals.UpdateRentalRequest;
import com.openclassrooms.api.model.response.EmptyResponse;
import com.openclassrooms.api.model.response.MessageResponse;
import com.openclassrooms.api.model.response.rental.RentalResponse;
import com.openclassrooms.api.model.response.rental.RentalsResponse;
import com.openclassrooms.api.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Rental REST controller
 */
@Slf4j
@Tag( name = "rental", description = "Rentals operations" )
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/rentals")
@ApiResponse( responseCode = "200")
@ApiResponse( responseCode = "401", content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = EmptyResponse.class)
))
public class RentalController {
    private final RentalService rentalService;
    private final ConversionService conversionService;

    /**
     * Constructor for RentalController class
     * @param rentalService RentalService
     * @param conversionService ConversionService
     */
    public RentalController(
            RentalService rentalService,
            ConversionService conversionService
    ) {
        this.rentalService = rentalService;
        this.conversionService = conversionService;
    }


    /**
     * Rentals list route
     * @return RentalsResponse
     */
    @Operation(summary = "get all", description = "Get all rentals")
    @GetMapping(
            path = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RentalsResponse getRentals() {

        Iterable<Rental> rentals = rentalService.listRentals();

        List<RentalResponse> rentalsList = new ArrayList<>();

        for (Rental rental : rentals) {
            rentalsList.add(conversionService.convert(rental, RentalResponse.class));
        }
        return RentalsResponse.builder()
                .rentals(rentalsList)
                .build();
    }


    /**
     * Get Rental route
     *
     * @param id Rental id
     * @return RentalResponse
     * @throws InvalidCredentialsException InvalidCredentialsException
     */
    @Operation(summary = "get", description = "Get rental by id")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RentalResponse getRental(@PathVariable int id) throws InvalidCredentialsException {

        Optional<Rental> optRental = rentalService.getRental(id);

        return optRental.map(
                        rental -> conversionService.convert(rental, RentalResponse.class))
                .orElseThrow(InvalidCredentialsException::new);
    }


    /**
     * Create Rental route
     *
     * @param request CreateRentalRequest
     * @param principal Principal
     * @return MessageResponse
     * @throws InvalidCredentialsException InvalidCredentialsException
     */
    @Operation(summary = "create", description = "Create new rental")
    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public MessageResponse postRental(
            @ModelAttribute CreateRentalRequest request,
            Principal principal
    ) throws InvalidCredentialsException {

        if (rentalService.saveRental(
                request.getName(),
                request.getSurface(),
                request.getPrice(),
                request.getPicture(),
                request.getDescription(),
                principal.getName()
        )) {
            return new MessageResponse("Rental created !");
        }
        throw new InvalidCredentialsException();
    }


    /**
     * Update Rental route
     *
     * @param id Rental id
     * @param request UpdateRentalRequest
     * @param principal Principal
     * @return MessageResponse
     * @throws InvalidCredentialsException InvalidCredentialsException
     */
    @Operation(summary = "update", description = "Update existing rental")
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public MessageResponse putRental(
            @PathVariable int id,
            @ModelAttribute UpdateRentalRequest request,
            Principal principal
    )
            throws InvalidCredentialsException {

        if (rentalService.updateRental(
                id,
                request.getName(),
                request.getSurface(),
                request.getPrice(),
                request.getDescription(),
                principal.getName()

        )
        ) {
            return new MessageResponse("Rental updated !");
        }
        throw new InvalidCredentialsException();
    }
}
