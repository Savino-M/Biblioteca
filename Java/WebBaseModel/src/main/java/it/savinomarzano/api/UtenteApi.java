package it.savinomarzano.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import it.savinomarzano.model.dto.RuoloDTO;
import it.savinomarzano.model.dto.UtenteDTO;

@Tag(name = "Utente", description = "API Utente")
public interface UtenteApi {

        /**
         * GET /user/getUserRole/{idUtente} : Restituisce il ruolo dell'utente
         * 
         * @param idUtente (required)
         * @return ruolo dell'utente con l'id specificato in input (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "getUserRole", summary = "Restituisce il ruolo dell'utente", tags = {
                        "Utente" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "ruolo dell'utente", content = {
                                                        @Content(mediaType = "application/json", schema = @Schema(implementation = RuoloDTO.class))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @GetMapping(value = "/user/getUserRole/{idUtente}", produces = { "application/json" })
        ResponseEntity<Object> getUserRole(
                        @Parameter(name = "idUtente", description = "ID dell'utente", required = true, in = ParameterIn.PATH) @PathVariable String idUtente);

        /**
         * POST /user/registraUtente : Inserisce un utente
         * 
         * @param utenteDTO DTO dell'utente (required)
         * @return esito dell'operazione (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "registraUtente", summary = "Inserisce un nuovo utente", tags = {
                        "Utente" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "esito dell'operazione", content = {
                                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @PostMapping(value = "/user/registraUtente", produces = { "application/json" }, consumes = {
                        "application/json" })
        ResponseEntity<Object> registraUtente(
                        @Parameter(name = "utenteDTO", description = "DTO dell'utente", required = true) @RequestBody UtenteDTO utenteDTO);

        /**
         * GET /user/login/{email}/{password} : Restituisce l'utenza
         * 
         * @param email    (required)
         * @param password (required)
         * @return token e utenza (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "login", summary = "Restituisce l'utenza", tags = {
                        "Utente" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Map contenente il token e l'utenza", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", properties = {
                                                        @StringToClassMapItem(key = "token", value = String.class),
                                                        @StringToClassMapItem(key = "utente", value = UtenteDTO.class),
                                        }))),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @GetMapping(value = "/user/login/{email}/{password}", produces = { "application/json" })
        ResponseEntity<Object> login(
                        @Parameter(name = "email", description = "email dell'utente", required = true, in = ParameterIn.PATH) @PathVariable String email,
                        @Parameter(name = "password", description = "password dell'utente", required = true, in = ParameterIn.PATH) @PathVariable String password);

        /**
         * GET /user/aggiornaToken/{idUtente} : Restituisce il token aggiornato
         * 
         * @param idUtente ID dell'utente richiesto (required)
         * @return nuovo token (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "aggiornaToken", summary = "Restituisce il nuovo token", tags = {
                        "Utente" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "nuovo token", content = {
                                                        @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @GetMapping(value = "/user/aggiornaToken/{idUtente}", produces = { "application/json" })
        ResponseEntity<Object> aggiornaToken(
                        @Parameter(name = "idUtente", description = "ID dell'utente", required = true, in = ParameterIn.PATH) @PathVariable String idUtente);

}
