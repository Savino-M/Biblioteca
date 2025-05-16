package it.savinomarzano.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO implements Serializable {

    private String id;

    private String nome;

    private String cognome;

    private String email;

    private String password;

    private String codiceFiscale;

    private RuoloDTO ruolo;

    private IndirizzoDTO indirizzo;

}
