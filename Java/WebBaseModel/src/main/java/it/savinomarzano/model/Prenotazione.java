package it.savinomarzano.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Prenotazione")
@CompoundIndex(def = "{'idUtente': 1, 'idElemento': 1}", unique = true)
public class Prenotazione implements Serializable {

    @Id
    private ObjectId id;

    @Field("idUtente")
    private ObjectId idUtente;

    // @DBRef
    // @Field("idUtente")
    // private Utente utente;

    @Field("idElemento")
    private ObjectId idElemento;

}
