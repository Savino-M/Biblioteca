package it.savinomarzano.controller;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import it.savinomarzano.api.UtenteApi;
import it.savinomarzano.exceptions.CustomException;
import it.savinomarzano.model.Utente;
import it.savinomarzano.model.dto.RuoloDTO;
import it.savinomarzano.model.dto.UtenteDTO;
import it.savinomarzano.service.UtenteService;
import it.savinomarzano.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
public class UtenteController implements UtenteApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtUtils jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Object> getUserRole(String idUtente) {

        log.info("Invocato /getUserRole");
        log.debug("Invocato /getUserRole");

        RuoloDTO ruoloDTO = null;

        try {

            ruoloDTO = utenteService.getUserRole(idUtente);

            log.info("Fine /getUserRole");
            log.debug("Fine /getUserRole");
            return new ResponseEntity<>(ruoloDTO, HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /getUserRole ", e);
            return new ResponseEntity<>("Si e' verificato un errore nel reperimento del ruolo utente",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> registraUtente(UtenteDTO utenteDTO) {

        log.info("Invocato /registraUtente");
        log.debug("Invocato /registraUtente");

        boolean isRegistrationCompleted = false;

        try {

            if (utenteDTO != null) {

                isRegistrationCompleted = utenteService.registraUtente(modelMapper.map(utenteDTO, Utente.class));

            }

            log.info("Fine /registraUtente");
            log.debug("Fine /registraUtente");
            return new ResponseEntity<>(isRegistrationCompleted, HttpStatus.OK);

        } catch (DuplicateKeyException e) {

            log.error("Esiste gia' un utente con questa mail ", e);
            return new ResponseEntity<>("Esiste gia' un utente con questa mail", HttpStatus.CONFLICT);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /registraUtente ", e);
            return new ResponseEntity<>("Si e' verificato un errore nella registrazione dell'utente",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> login(String email, String password) {

        log.info("Invocato /login");
        log.debug("Invocato /login");

        Map<String, Object> result = new HashMap<>();

        try {

            // Controllo se l'utente e' registrato
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            if (authentication.isAuthenticated()) {

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Genero il token
                String token = jwtService.generateToken(email);
                result.put("token", token);

                // Ricavo l'utente da DB
                Utente utente = utenteService.getUserByEmailAndPassword(email,
                        ((UserDetails) authentication.getPrincipal()).getPassword());

                if (utente != null) {

                    UtenteDTO utenteDTO = modelMapper.map(utente, UtenteDTO.class);
                    result.put("utente", utenteDTO);

                }

            }

            log.info("Fine /login");
            log.debug("Fine /login");
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (BadCredentialsException e) {

            log.error("Si e' verificato un errore in /login. Utente non valido ", e);
            return new ResponseEntity<>("Credenziali errate", HttpStatus.UNAUTHORIZED);

        } catch (CustomException e) {

            log.error("Si e' verificato un errore in /login ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /login ", e);
            return new ResponseEntity<>("Si e' verificato un errore nel login", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> aggiornaToken(String idUtente) {

        log.info("Invocato /aggiornaToken");
        log.debug("Invocato /aggiornaToken");

        String token = "";

        try {

            // Prendo l'utente
            Utente utente = utenteService.getUserById(idUtente);

            // Genero il token
            token = jwtService.generateToken(utente.getEmail());

            log.info("Fine /aggiornaToken");
            log.debug("Fine /aggiornaToken");
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /aggiornaToken ", e);
            return new ResponseEntity<>("Si e' verificato un errore", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}