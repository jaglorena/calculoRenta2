package com.unicaes.contabilidad.descuentos.controller;

import com.unicaes.contabilidad.descuentos.comunes.Calculos;
import com.unicaes.contabilidad.descuentos.dto.ConstanciaDTO;
import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.service.EmpleadoService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/constancia")
public class ConstanciaController {
    private EmpleadoService empleadoService;
    private Calculos calculos;

    @GetMapping
    public String constancia(){
        return "constancia";
    }

    @GetMapping("/{dui}")
    public String generarConstancia(
            @PathVariable(value = "dui") String dui,
            Model model
    ) {
        Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorDui(dui);
        if(empleadoOptional.isPresent()){
            Empleado empleado = empleadoOptional.get();
            List<Ingreso> ingresos = new ArrayList<>();
            List<Descuento> descuentos = new ArrayList<>();
            String nombreCompleto = String
                    .format("%s %s", empleado.getNombres(), empleado.getApellidos()).trim();
            String fechaInicio = String
                    .format(
                            "%d de %s de %d",
                            empleado.getFechaIngreso().getDayOfMonth(),
                            empleado.getFechaIngreso().getMonth()
                                    .getDisplayName(TextStyle.FULL, new Locale("es", "SV", "ES")),
                            empleado.getFechaIngreso().getYear()
                    );
            String fechaExpedicion = String
                    .format("%d de %s de %d",
                            LocalDate.now().getDayOfMonth(),
                            LocalDate.now().getMonth()
                                    .getDisplayName(TextStyle.FULL, new Locale("es", "SV", "ES")),
                            LocalDate.now().getYear()
                    );
            ingresos.add(
                    Ingreso.builder()
                            .id(0)
                            .descripcion("SALARIO")
                            .monto(empleado.getSalario())
                            .build()
            );
            ingresos.addAll(empleado.getIngresos());
            descuentos.add(
                    calculos.descuentoIsss(empleado)
            );
            descuentos.add(
                    calculos.descuentoAFP(empleado)
            );
            descuentos.add(
                    calculos.descuentoISR(empleado)
            );
            descuentos.addAll(empleado.getDescuentos());

            model.addAttribute("constanciaDto",
                    ConstanciaDTO.builder()
                            .nombreCompleto(nombreCompleto)
                            .fechaIngreso(fechaInicio)
                            .cargo(empleado.getCargo())
                            .ingresos(ingresos)
                            .descuentos(descuentos)
                            .fechaEmision(fechaExpedicion)
                            .build()
            );
        }
        return "constancia";
    }
}
