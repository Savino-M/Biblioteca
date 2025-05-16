package it.savinomarzano.model.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementoDTO implements Serializable {

    private String id;

    private String descrizione;

    private ImmagineDTO copertina;

    private List<ImmagineDTO> immaginiList;

}
