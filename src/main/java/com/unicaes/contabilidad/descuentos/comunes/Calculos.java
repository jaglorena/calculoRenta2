package com.unicaes.contabilidad.descuentos.comunes;

import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.entity.Retencion;
import com.unicaes.contabilidad.descuentos.service.RetencionService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class Calculos {
    private RetencionService retencionService;
    public static final double PORCENTAJE_ISSS = 0.03;
    public static final double PORCENTAJE_AFP = 0.0725;

    public Descuento descuentoIsss(Empleado empleado) {
        return Descuento.builder()
                .id(0)
                .descripcion("ISSS")
                .monto(totalIngresos(empleado) * PORCENTAJE_ISSS)
                .build();
    }

    public Descuento descuentoAFP(Empleado empleado) {
        return Descuento.builder()
                .id(0)
                .descripcion("AFP")
                .monto(totalIngresos(empleado) * PORCENTAJE_AFP)
                .build();
    }

    public Ingreso calculoVacacion (Empleado empleado) {
        int diasPagados = 15;
        double salarioDiario = empleado.getSalario()/30;

        return Ingreso.builder()
                .id (0)
                .descripcion("Vacaciones")
                .monto(salarioDiario * diasPagados)
                .build();
    }

    public Ingreso calculoAguinaldo (Empleado empleado){
        double salarioDiario = empleado.getSalario()/30;
        long aniosAntiguedad = ChronoUnit.YEARS.between(empleado.getFechaIngreso(), LocalDate.now());
        int diasPagados;
        if (aniosAntiguedad <= 3) {
            diasPagados = 15;
        } else if (aniosAntiguedad <= 10 ) {
            diasPagados = 19;
        } else {
            diasPagados = 21;
        }
        return Ingreso.builder()
                .id (0)
                .descripcion("Aguinaldo")
                .monto(salarioDiario * diasPagados)
                .build();
    }

    public Descuento descuentoISR(Empleado empleado) {
        double totalDeducciones = descuentoIsss(empleado).getMonto() + descuentoAFP(empleado).getMonto();
        double salarioDeducciones = totalIngresos(empleado) - totalDeducciones;
        double salarioExcedente = 0.0;

        Retencion retencion = retencionService.obtenerRetencionPorSalario(salarioDeducciones);
        salarioExcedente = salarioDeducciones - retencion.getExceso();
        salarioExcedente = salarioExcedente * retencion.getPorcentaje();

        return Descuento.builder()
                .id(0)
                .descripcion("ISR")
                .monto(salarioExcedente + retencion.getCuotaFija())
                .build();
    }

    public double totalIngresos(Empleado empleado) {
        List<Ingreso> ingresos = Optional.ofNullable(empleado.getIngresos()).orElse(Collections.emptyList());
        return empleado.getSalario() + ingresos.stream().mapToDouble(Ingreso::getMonto).sum();
    }

}
