package it.savinomarzano.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.savinomarzano.model.Prenotazione;

@Repository
public interface IPrenotazioneRepository extends MongoRepository<Prenotazione, ObjectId> {

    List<Prenotazione> findByIdUtente(String idUtente);

}
