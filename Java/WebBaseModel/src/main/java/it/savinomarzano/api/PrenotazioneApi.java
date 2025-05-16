package it.savinomarzano.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.savinomarzano.model.dto.PrenotazioneDTO;

@Tag(name = "Prenotazione", description = "API Prenotazione")
public interface PrenotazioneApi {

        /**
         * GET /prenotazione/getPrenotazioniByUser/{idUtente} : Restituisce le
         * prenotazioni dell'utente
         *
         * @param idUtente (required)
         * @return prenotazioni dell'utente (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "getPrenotazioniByUser", summary = "Restituisce le prenotazioni dell'utente", tags = {
                        "Prenotazione" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "lista di prenotazioni dell'utente", content = {
                                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrenotazioneDTO.class)))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @GetMapping(value = "/prenotazione/getPrenotazioniByUser/{idUtente}", produces = { "application/json" })
        ResponseEntity<Object> getPrenotazioniByUser(
                        @Parameter(name = "idUtente", description = "ID utente di cui richiedere le prenotazioni", required = true, in = ParameterIn.PATH) @PathVariable String idUtente);

        /**
         * POST /prenotazione/inserisciPrenotazione : Inserisce la prenotazione
         *
         * @param prenotazioneDTO (required)
         * @return esito dell'operazione (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "inserisciPrenotazione", summary = "Inserisce la prenotazione", tags = {
                        "Prenotazione" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "esito dell'operazione", content = {
                                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @PostMapping(value = "/prenotazione/inserisciPrenotazione", produces = { "application/json" }, consumes = {
                        "application/json" })
        ResponseEntity<Object> inserisciPrenotazione(
                        @Parameter(name = "prenotazioneDTO", description = "DTO della prenotazione", required = true) @RequestBody PrenotazioneDTO prenotazioneDTO);

        /**
         * DELETE /prenotazione/eliminaPrenotazione/{idPrenotazione} : Elimina la
         * prenotazione con id specificato
         *
         * @param idPrenotazione della prenotazione da eliminare (required)
         * @return esito dell'operazione (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "eliminaPrenotazione", summary = "Elimina la prenotazione con l'ID specificato", tags = {
                        "Prenotazione" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "esito dell'operazione", content = {
                                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @DeleteMapping(value = "/prenotazione/eliminaPrenotazione/{idPrenotazione}", produces = { "application/json" })
        ResponseEntity<Object> eliminaPrenotazione(
                        @Parameter(name = "idPrenotazione", description = "ID della prenotazione da eliminare", required = true, in = ParameterIn.PATH) @PathVariable String idPrenotazione);

}
