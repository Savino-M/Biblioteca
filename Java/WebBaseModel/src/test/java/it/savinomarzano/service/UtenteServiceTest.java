package it.savinomarzano.service;

import it.savinomarzano.model.Utente;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import it.savinomarzano.model.dto.RuoloDTO;

@SpringBootTest
public class UtenteServiceTest {

	@Autowired
	private UtenteService utenteService;

	@Test
	public void getUserByEmailAndPassword() throws Exception {

		String email = "abc";
		String password = "abc";
		Utente expected = new Utente();
		Utente actual = utenteService.getUserByEmailAndPassword(email, password);

	}

	@Test
	public void getUserRole() throws Exception {

		String idUtente = "abc";
		RuoloDTO expected = new RuoloDTO();
		RuoloDTO actual = utenteService.getUserRole(idUtente);

	}

	@Test
	public void registraUtente() {

		Utente utente = new Utente();
		boolean expected = true;
		boolean actual = utenteService.registraUtente(utente);

	}

	@Test
	public void getUserById() {

		String idUtente = "abc";
		Utente expected = new Utente();
		Utente actual = utenteService.getUserById(idUtente);

	}

}
