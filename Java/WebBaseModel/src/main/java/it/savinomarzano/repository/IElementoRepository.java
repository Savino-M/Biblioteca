package it.savinomarzano.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import it.savinomarzano.model.Elemento;
import it.savinomarzano.model.dto.ElementoDTO;

@Repository
public interface IElementoRepository extends MongoRepository<Elemento, ObjectId> {

        @Query("select new it.savinomarzano.model.dto.ElementoDTO(e.id, e.descrizione, e.copertina, e.immaginiList) from Elemento e ")
        List<ElementoDTO> getAllElements();

}
