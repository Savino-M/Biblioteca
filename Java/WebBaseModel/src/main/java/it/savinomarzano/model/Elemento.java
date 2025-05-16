package it.savinomarzano.model;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.savinomarzano.model.dto.ImmagineDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Elemento")
public class Elemento implements Serializable {

    @Id
    private ObjectId id;

    @Field("descrizione")
    private String descrizione;

    @Field("copertina")
    private ImmagineDTO copertina;

    @Field("immaginiList")
    private List<ImmagineDTO> immaginiList;

}
