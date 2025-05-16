package it.savinomarzano.service;

import it.savinomarzano.model.Elemento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class ElementoServiceTest {

	@Autowired
	private ElementoService elementoService;

	@Test
	public void getAllElements() {

		PageRequest pageable = PageRequest.of(0, 10);
		Page<Elemento> expected = null;
		Page<Elemento> actual = elementoService.getAllElements(pageable);

	}

	@Test
	public void eliminaElemento() {

		String idElemento = "abc";
		boolean expected = true;
		boolean actual = elementoService.eliminaElemento(idElemento);

	}

	@Test
	public void inserisciElemento() {

		Elemento elemento = new Elemento();
		boolean expected = true;
		boolean actual = elementoService.inserisciElemento(elemento);

	}

	@Test
	public void getElementoById() throws Exception {

		String idElemento = "abc";
		Elemento expected = new Elemento();
		Elemento actual = elementoService.getElementoById(new ObjectId(idElemento));

	}

	@Test
	public void aggiornaElementi() {

		elementoService.aggiornaElementi();

	}

}
