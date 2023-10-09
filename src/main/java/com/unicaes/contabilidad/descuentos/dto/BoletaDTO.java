package com.unicaes.contabilidad.descuentos.dto;

import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoletaDTO {
    private String nombreCompleto;
    private String cargo;
    private String periodo;
    private List<Ingreso> ingresos;
    private double totalIngresos;
    private List<Descuento> descuentos;
    private double totalDescuentos;
    private List<Descuento> retenciones;
    private double totalRetenciones;
    private double totalAPagar;
    private List<Ingreso> indemnizaciones;
    private double totalIndemnizacion;
    private String dui;
}
