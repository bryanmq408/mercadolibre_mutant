package com.meli.mutants.mutant.application.rest;

import com.meli.mutants.mutant.application.request.DNA;
import com.meli.mutants.mutant.application.response.Stats;
import com.meli.mutants.mutant.domain.service.MutantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Mutant", description = "API Mutantes")
@RestController
public class ApiController {

    private MutantService service;

    @Autowired
    public ApiController(MutantService service){
        this.service = service;
    }

    @Operation(summary = "Check if the DNA sequence is mutant", description = "Receives a DNA sequence and returns Successful if it is a mutant, otherwise it is forbidden.", tags = {"Mutant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DNA is Mutant"),
            @ApiResponse(responseCode = "403", description = "DNA is not Mutant")
    })
    @PostMapping("/mutant")
    public ResponseEntity isMutant(@Valid @RequestBody DNA request) {
        return service.isMutant(request.getDna()) ?
                ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Get statistics from DNA checks", description = "Returns the ratio between DNA human and mutant", tags = {"Mutant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")})
    @GetMapping("/stats")
    public ResponseEntity<Stats> getStats() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getStats());
    }

    @Operation(summary = "Ping the app", description = "Ping the app", tags = {"Mutant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok")})
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
