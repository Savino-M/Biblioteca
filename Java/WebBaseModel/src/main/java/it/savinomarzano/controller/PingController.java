package it.savinomarzano.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/webBaseModel")
public class PingController {

    /**
     * Metodo di ping
     *
     * @return esito
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    private ResponseEntity<Object> ping() {

        try {

            return new ResponseEntity<>("OK", HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>("Si e' verificato un errore in /ping", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
