package it.savinomarzano.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.savinomarzano.enums.TipoRuoloEnum;
import it.savinomarzano.exceptions.CustomException;
import it.savinomarzano.model.Utente;
import it.savinomarzano.model.dto.RuoloDTO;
import it.savinomarzano.repository.IUtenteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UtenteService implements UserDetailsService {

    @Autowired
    private IUtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Utente getUserByEmailAndPassword(String email, String password) throws Exception {

        log.info("Inizio servizio getUserByEmailAndPassword");

        Utente utente = null;

        try {

            log.info("Ricerca su DB dell'utente");

            // Trovo l'utente con quella mail e password
            utente = utenteRepository.findByEmailAndPassword(email, password);

            if (utente != null) {

                log.info("Utente trovato");

            } else {

                throw new CustomException("Nessun utente trovato");

            }

            log.info("Fine servizio getUserByEmailAndPassword");
            return utente;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio getUserByEmailAndPassword");
            throw e;

        }

    }

    public RuoloDTO getUserRole(String idUtente) throws Exception {

        log.info("Inizio servizio getUserRole");

        RuoloDTO ruoloDTO = null;

        try {

            log.info("Ricerca su DB dell'utente");

            Utente utente = utenteRepository.findById(new ObjectId(idUtente)).get();

            if (utente != null) {

                log.info("Utente trovato. Prendo il ruolo");

                ruoloDTO = utente.getRuolo();

            }

            log.info("Fine servizio getUserRole");
            return ruoloDTO;

        } catch (NoSuchElementException e) {

            log.info("Nessun utente trovato");
            return null;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio getUserRole");
            throw e;

        }

    }

    public boolean registraUtente(Utente utente) {

        log.info("Inizio servizio registraUtente");

        boolean isRegistrationCompleted = false;

        try {

            // Setto il ruolo standard all'utente
            utente.setRuolo(TipoRuoloEnum.USER.getRuolo());

            // Cripto password dell'utente
            utente.setPassword(encoder.encode(utente.getPassword()));

            // Registro l'utente su DB
            utenteRepository.save(utente);

            log.info("Utente correttamente registrato");
            isRegistrationCompleted = true;

            log.info("Fine servizio registraUtente");
            return isRegistrationCompleted;

        } catch (DuplicateKeyException e) {

            log.error("E' gia' presente un utente con la stessa mail");
            throw e;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio registraUtente");
            throw e;

        }

    }

    public Utente getUserById(String idUtente) {

        log.info("Inizio servizio getUserById");

        Utente utente = null;

        try {

            utente = utenteRepository.findById(new ObjectId(idUtente)).get();

            log.info("Fine servizio getUserById");
            return utente;

        } catch (NoSuchElementException e) {

            log.info("Nessun utente trovato");
            return null;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio getUserById");
            throw e;

        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utente utente = utenteRepository.findByEmail(email);

        UserDetails userDetails = new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(utente.getRuolo().getDescrizione()));
                return authorities;

            }

            @Override
            public String getPassword() {

                return utente.getPassword();

            }

            @Override
            public String getUsername() {

                return utente.getEmail();

            }

            @Override
            public boolean isAccountNonExpired() {

                return true;

            }

            @Override
            public boolean isAccountNonLocked() {

                return true;

            }

            @Override
            public boolean isCredentialsNonExpired() {

                return true;

            }

            @Override
            public boolean isEnabled() {

                return true;

            }

        };

        return userDetails;

    }

}
