package it.savinomarzano.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.savinomarzano.model.dto.ElementoDTO;

@Tag(name = "Elemento", description = "API Elemento")
public interface ElementoApi {

        /**
         * GET /element/getAllElements/{pagina}/{elementiPerPagina} : Restituisce tutti
         * gli elementi
         * 
         * @param pagina
         * @param elementiPerPagina
         * @return Map contenente una lista di elementi e altre informazioni (status
         *         code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "getAllElements", summary = "Restituisce tutti gli elementi", tags = {
                        "Elemento" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Map contenente una lista di elementi e altre informazioni", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", properties = {
                                                        @StringToClassMapItem(key = "content", value = ElementoDTO[].class),
                                                        @StringToClassMapItem(key = "currentPage", value = Integer.class),
                                                        @StringToClassMapItem(key = "totalItems", value = Long.class),
                                                        @StringToClassMapItem(key = "totalPages", value = Integer.class),
                                                        @StringToClassMapItem(key = "pageSize", value = Integer.class)
                                        }))),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @GetMapping(value = "/element/getAllElements/{pagina}/{elementiPerPagina}", produces = { "application/json" })
        ResponseEntity<Object> getAllElements(
                        @Parameter(name = "pagina", description = "pagina", required = false, in = ParameterIn.PATH) @PathVariable int pagina,
                        @Parameter(name = "elementiPerPagina", description = "elementi per pagina", required = false, in = ParameterIn.PATH) @PathVariable int elementiPerPagina);

        /**
         * PATCH /element/aggiornaElementi/ : aggiorna elementi
         *
         * @return OK (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "aggiornaElementi", summary = "Aggiorna tutti gli elementi", tags = {
                        "Elemento" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "OK"),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @PatchMapping(value = "/element/aggiornaElementi", produces = { "application/json" })
        ResponseEntity<Object> aggiornaElementi();

        /**
         * DELETE /element/eliminaElemento/{idElemento} : elimina un elemento
         *
         * @param idElemento da eliminare (required)
         * @return esito dell'operazione (status code 200)
         *         or Bad request (status code 400)
         *         or Forbidden (status code 403)
         *         or Resource not found (status code 404)
         *         or Internal server error (status code 500)
         */
        @Operation(operationId = "eliminaElemento", summary = "Elimina un elemento", tags = {
                        "Elemento" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "esito dell'operazione", content = {
                                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
                                        }),
                                        @ApiResponse(responseCode = "400", description = "Bad request"),
                                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                                        @ApiResponse(responseCode = "404", description = "Resource not found"),
                                        @ApiResponse(responseCode = "500", description = "Internal server error")
                        })
        @DeleteMapping(value = "/element/eliminaElemento/{idElemento}", produces = { "application/json" })
        ResponseEntity<Object> eliminaElemento(
                        @Parameter(name = "idElemento", description = "id dell'elemento da eliminare", required = true, in = ParameterIn.PATH) @PathVariable String idElemento);

}
