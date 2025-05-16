package it.savinomarzano.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.savinomarzano.model.Utente;

@Repository
public interface IUtenteRepository extends MongoRepository<Utente, ObjectId> {

    Utente findByEmailAndPassword(String email, String password);

    Utente findByEmail(String username);

}
