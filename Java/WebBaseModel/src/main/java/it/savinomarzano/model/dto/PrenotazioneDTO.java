package it.savinomarzano.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneDTO implements Serializable {

    private String id;

    private String idUtente;

    private String idElemento;

    private ElementoDTO elemento;

}
