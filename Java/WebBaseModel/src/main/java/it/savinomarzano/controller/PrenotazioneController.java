package it.savinomarzano.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import it.savinomarzano.api.PrenotazioneApi;
import it.savinomarzano.model.Prenotazione;
import it.savinomarzano.model.dto.ElementoDTO;
import it.savinomarzano.model.dto.PrenotazioneDTO;
import it.savinomarzano.service.ElementoService;
import it.savinomarzano.service.PrenotazioneService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
public class PrenotazioneController implements PrenotazioneApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private ElementoService elementoService;

    @Override
    public ResponseEntity<Object> getPrenotazioniByUser(String idUtente) {

        log.info("Invocato /getPrenotazioniByUser");
        log.debug("Invocato /getPrenotazioniByUser");

        List<PrenotazioneDTO> prenotazioniDTOList = new ArrayList<>();

        try {

            List<Prenotazione> prenotazioniList = prenotazioneService.getPrenotazioniByUser(idUtente);

            prenotazioniDTOList = prenotazioniList.stream().map(p -> {
                PrenotazioneDTO prenotazioneDTO = modelMapper.map(p, PrenotazioneDTO.class);
                try {
                    prenotazioneDTO.setElemento(
                            modelMapper.map(elementoService.getElementoById(p.getIdElemento()), ElementoDTO.class));
                } catch (Exception e) {
                    log.error("Si e' verificato un errore in /getPrenotazioniByUser ", e);
                    return null;
                }
                return prenotazioneDTO;
            }).collect(Collectors.toList());

            log.info("Fine /getPrenotazioniByUser");
            log.debug("Fine /getPrenotazioniByUser");
            return new ResponseEntity<>(prenotazioniDTOList, HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /getPrenotazioniByUser ", e);
            return new ResponseEntity<>("Si e' verificato un errore durante il caricamento delle prenotazioni",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> inserisciPrenotazione(PrenotazioneDTO prenotazioneDTO) {

        log.info("Invocato /inserisciPrenotazione");
        log.debug("Invocato /inserisciPrenotazione");

        boolean isReservationCompleted = false;

        try {

            if (prenotazioneDTO != null) {

                isReservationCompleted = prenotazioneService
                        .inserisciPrenotazione(modelMapper.map(prenotazioneDTO, Prenotazione.class));

            }

            log.info("Fine /inserisciPrenotazione");
            log.debug("Fine /inserisciPrenotazione");
            return new ResponseEntity<>(isReservationCompleted, HttpStatus.OK);

        } catch (DuplicateKeyException e) {

            log.error("Risulta gia' una prenotazione ", e);
            return new ResponseEntity<>("Risulta gia' una prenotazione", HttpStatus.CONFLICT);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /inserisciPrenotazione ", e);
            return new ResponseEntity<>("Si e' verificato un errore durante la prenotazione",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> eliminaPrenotazione(String idPrenotazione) {

        log.info("Invocato /eliminaPrenotazione");
        log.debug("Invocato /eliminaPrenotazione");

        boolean isReservationCancelled = false;

        try {

            isReservationCancelled = prenotazioneService.eliminaPrenotazione(idPrenotazione);

            log.info("Fine /eliminaPrenotazione");
            log.debug("Fine /eliminaPrenotazione");
            return new ResponseEntity<>(isReservationCancelled, HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /eliminaPrenotazione ", e);
            return new ResponseEntity<>("Si e' verificato un errore nell'eliminazione della prenotazione",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}