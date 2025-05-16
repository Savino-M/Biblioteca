package it.savinomarzano.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import it.savinomarzano.model.Elemento;
import it.savinomarzano.model.dto.ImmagineDTO;
import it.savinomarzano.repository.IElementoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ElementoService {

    @Value("${images.path}")
    private String path;

    @Autowired
    private IElementoRepository elementoRepository;

    public Page<Elemento> getAllElements(PageRequest pageable) {

        log.info("Inizio servizio getAllElements");

        Page<Elemento> elementiList;

        try {

            log.info("Ricerca su DB degli elementi");

            elementiList = elementoRepository.findAll(pageable);

            log.info("Fine servizio getAllElements");
            return elementiList;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio getAllElements");
            throw e;

        }

    }

    public boolean eliminaElemento(String idElemento) {

        log.info("Inizio servizio eliminaElemento");

        boolean isElementCancelled = false;

        try {

            log.info("Eliminazione su DB dell'elemento");

            elementoRepository.deleteById(new ObjectId(idElemento));

            log.info("Elemento correttamente eliminato");
            isElementCancelled = true;

            log.info("Fine servizio eliminaElemento");
            return isElementCancelled;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio eliminaElemento");
            throw e;

        }

    }

    public boolean inserisciElemento(Elemento elemento) {

        log.info("Inizio servizio inserisciElemento");

        boolean isElementInserted = false;

        try {

            elemento = elementoRepository.save(elemento);

            log.info("Elemento correttamente inserito");
            isElementInserted = true;

            log.info("Fine servizio inserisciElemento");
            return isElementInserted;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio inserisciElemento");
            throw e;

        }

    }

    public Elemento getElementoById(ObjectId idElemento) throws Exception {

        log.info("Inizio servizio getElementoById");

        Elemento elemento = null;

        try {

            log.info("Ricerca su DB dell'elemento");

            elemento = elementoRepository.findById(idElemento).get();

            if (elemento != null) {

                log.info("Elemento trovato");

            }

            log.info("Fine servizio getElementoById");
            return elemento;

        } catch (NoSuchElementException e) {

            log.info("Nessun elemento trovato");
            return null;

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio getElementoById");
            throw e;

        }

    }

    public void aggiornaElementi() {

        log.info("Inizio servizio aggiornaElementi");

        try {

            List<Elemento> listaElementi = new ArrayList<>();

            Stream.of(new File(path).listFiles())
                    .filter(file -> file.isDirectory())
                    .map(File::getName).forEach(s -> {
                        Elemento elemento = new Elemento();
                        elemento.setDescrizione(s);
                        Stream.of(new File(path + "\\" + s).listFiles())
                                .filter(c -> {
                                    int index = c.toPath().getFileName().toString().lastIndexOf(".");
                                    String nomeFile = c.toPath().getFileName().toString().substring(0, index);
                                    return nomeFile.equalsIgnoreCase("copertina");
                                })
                                .forEach(a -> {
                                    ImmagineDTO copertina = new ImmagineDTO(a.getPath());
                                    elemento.setCopertina(copertina);
                                });
                        List<ImmagineDTO> listaImmagini = new ArrayList<>();
                        Stream.of(new File(path + "\\" + s).listFiles())
                                .forEach(a -> {
                                    ImmagineDTO immagine = new ImmagineDTO(a.getPath());
                                    listaImmagini.add(immagine);
                                });
                        elemento.setImmaginiList(listaImmagini);
                        listaElementi.add(elemento);
                    });

            elementoRepository.saveAll(listaElementi);

        } catch (Exception e) {

            log.error("Si e' verificato un errore nel servizio aggiornaElementi");
            throw e;

        }

    }

}
