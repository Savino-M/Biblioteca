package it.savinomarzano.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndirizzoDTO implements Serializable {

    private String citta;

    private String codiceZip;

    private String indirizzo;

}
