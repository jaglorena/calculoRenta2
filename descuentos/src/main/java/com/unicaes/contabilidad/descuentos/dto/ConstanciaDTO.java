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
public class ConstanciaDTO {
    private String nombreCompleto;
    private String cargo;
    private String fechaIngreso;
    private List<Ingreso> ingresos;
    private List<Descuento> descuentos;
    private String fechaEmision;
}
