package it.savinomarzano.enums;

import it.savinomarzano.model.dto.RuoloDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoRuoloEnum {

    USER(new RuoloDTO("1", "User")),
    ADMIN(new RuoloDTO("2", "Admin"));

    private RuoloDTO ruolo;

}
