package it.savinomarzano.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import it.savinomarzano.api.ElementoApi;
import it.savinomarzano.model.Elemento;
import it.savinomarzano.model.dto.ElementoDTO;
import it.savinomarzano.service.ElementoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
public class ElementoController implements ElementoApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ElementoService elementoService;

    @Override
    public ResponseEntity<Object> getAllElements(int pagina, int elementiPerPagina) {

        log.info("Invocato /getAllElements");
        log.debug("Invocato /getAllElements");

        Map<String, Object> response = new HashMap<>();

        try {

            // Prendo tutti gli elementi
            Page<Elemento> elementiList = elementoService.getAllElements(PageRequest.of(pagina, elementiPerPagina));

            List<ElementoDTO> elementiDTOList = elementiList.stream().map(e -> modelMapper.map(e, ElementoDTO.class))
                    .collect(Collectors.toList());

            response.put("content", elementiDTOList);
            response.put("currentPage", elementiList.getNumber());
            response.put("totalItems", elementiList.getTotalElements());
            response.put("totalPages", elementiList.getTotalPages());
            response.put("pageSize", elementiList.getSize());

            log.info("Fine /getAllElements");
            log.debug("Fine /getAllElements");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /getAllElements ", e);
            return new ResponseEntity<>(
                    "Si e' verificato un errore durante il reperimento degli elementi da visualizzare",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> aggiornaElementi() {

        log.info("Invocato /aggiornaElementi");
        log.debug("Invocato /aggiornaElementi");

        try {

            elementoService.aggiornaElementi();

            log.info("Fine /aggiornaElementi");
            log.debug("Fine /aggiornaElementi");
            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /aggiornaElementi ", e);
            return new ResponseEntity<>("Si e' verificato un errore durante l'aggiornamento degli elementi",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> eliminaElemento(String idElemento) {

        log.info("Invocato /eliminaElemento");
        log.debug("Invocato /eliminaElemento");

        boolean isElementCancelled = false;

        try {

            isElementCancelled = elementoService.eliminaElemento(idElemento);

            log.info("Fine /eliminaElemento");
            log.debug("Fine /eliminaElemento");
            return new ResponseEntity<>(isElementCancelled, HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /eliminaElemento ", e);
            return new ResponseEntity<>("Si e' verificato un errore durante l'eliminazione dell'elemento",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}