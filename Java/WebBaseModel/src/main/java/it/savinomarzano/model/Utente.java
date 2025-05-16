package it.savinomarzano.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.savinomarzano.model.dto.IndirizzoDTO;
import it.savinomarzano.model.dto.RuoloDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Utente")
public class Utente implements Serializable {

    @Id
    private ObjectId id;

    @Field("nome")
    private String nome;

    @Field("cognome")
    private String cognome;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("codiceFiscale")
    private String codiceFiscale;

    @Field("ruolo")
    private RuoloDTO ruolo;

    @Field("indirizzo")
    private IndirizzoDTO indirizzo;

}
