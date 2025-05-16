package it.savinomarzano.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import it.savinomarzano.model.Prenotazione;
import it.savinomarzano.repository.IPrenotazioneRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PrenotazioneService {

    @Autowired
    private IPrenotazioneRepository prenotazioneRepository;

    public List<Prenotazione> getPrenotazioniByUser(String idUtente) {

        log.info("Inizio servizio getPrenotazioniByUser");

        List<Prenotazione> prenotazioniList = new ArrayList<>();

        try {

            log.info("Ricerca su DB delle prenotazioni");

            prenotazioniList = prenotazioneRepository.findByIdUtente(idUtente);

            log.info("Fine servizio getPrenotazioniByUser");
            return prenotazioniList;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio getPrenotazioniByUser");
            throw e;

        }

    }

    public boolean inserisciPrenotazione(Prenotazione prenotazione) {

        log.info("Inizio servizio inserisciPrenotazione");

        boolean isReservationCompleted = false;

        try {

            prenotazioneRepository.save(prenotazione);

            log.info("Prenotazione correttamente avvenuta");
            isReservationCompleted = true;

            log.info("Fine servizio inserisciPrenotazione");
            return isReservationCompleted;

        } catch (DuplicateKeyException e) {

            log.error("Risulta gia' una prenotazione per questo elemento");
            throw e;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio inserisciPrenotazione");
            throw e;

        }

    }

    public boolean eliminaPrenotazione(String idPrenotazione) {

        log.info("Inizio servizio eliminaPrenotazione");

        boolean isReservationCancelled = false;

        try {

            log.info("Eliminazione su DB della prenotazione");

            prenotazioneRepository.deleteById(new ObjectId(idPrenotazione));

            log.info("Prenotazione correttamente eliminata");
            isReservationCancelled = true;

            log.info("Fine servizio eliminaPrenotazione");
            return isReservationCancelled;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio eliminaPrenotazione");
            throw e;

        }

    }

}
