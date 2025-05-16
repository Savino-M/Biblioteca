package it.savinomarzano.service;

import it.savinomarzano.model.Prenotazione;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PrenotazioneServiceTest {

	@Autowired
	private PrenotazioneService prenotazioneService;

	@Test
	public void getPrenotazioniByUser() {

		String idUtente = "abc";
		List<Prenotazione> expected = new ArrayList<>();
		List<Prenotazione> actual = prenotazioneService.getPrenotazioniByUser(idUtente);

	}

	@Test
	public void inserisciPrenotazione() {

		Prenotazione prenotazione = new Prenotazione();
		boolean expected = true;
		boolean actual = prenotazioneService.inserisciPrenotazione(prenotazione);

	}

	@Test
	public void eliminaPrenotazione() {

		String idPrenotazione = "abc";
		boolean expected = true;
		boolean actual = prenotazioneService.eliminaPrenotazione(idPrenotazione);

	}

}
