package com.unicaes.contabilidad.descuentos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmpleadoDTO {
    private String dui;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private LocalDate fechaSalida;
    @Builder.Default
    private Double salario = 0.0;
    @Builder.Default
    private Double totalIngresos = 0.0;
    @Builder.Default
    private Double totalDescuentos = 0.0;
}
