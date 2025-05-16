package it.savinomarzano.consumer;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import it.savinomarzano.configuration.RabbitMQConfig;
import it.savinomarzano.model.Prenotazione;
import it.savinomarzano.model.dto.PrenotazioneDTO;
import it.savinomarzano.service.PrenotazioneService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PrenotazioneConsumer {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(PrenotazioneDTO prenotazioneDTO) {

        log.info("Invocato /inserisciPrenotazione");

        try {

            if (prenotazioneDTO != null) {

                prenotazioneService.inserisciPrenotazione(modelMapper.map(prenotazioneDTO, Prenotazione.class));

            }

            log.info("Fine /inserisciPrenotazione");

        } catch (DuplicateKeyException e) {

            log.error("Risulta gia' una prenotazione ", e);

        } catch (Exception e) {

            log.error("Si e' verificato un errore in /inserisciPrenotazione ", e);

        }

    }

}
