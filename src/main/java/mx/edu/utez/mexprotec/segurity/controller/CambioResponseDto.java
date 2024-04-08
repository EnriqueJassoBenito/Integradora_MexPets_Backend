package mx.edu.utez.mexprotec.segurity.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CambioResponseDto {
    private String mensaje;
    private boolean error;

    public CambioResponseDto(String mensaje, boolean error) {
        this.mensaje = mensaje;
        this.error = error;
    }



}
