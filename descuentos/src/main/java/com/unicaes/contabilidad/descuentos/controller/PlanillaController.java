package com.unicaes.contabilidad.descuentos.controller;

import com.unicaes.contabilidad.descuentos.comunes.Calculos;
import com.unicaes.contabilidad.descuentos.dto.EmpleadoDTO;
import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/planilla")
public class PlanillaController {
    private EmpleadoService empleadoService;
    private Calculos calculos;
    @GetMapping
    public String planilla(Model model) {
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        List<EmpleadoDTO> empleadoDTOS = new ArrayList<>();
        List<Descuento> descuentos = new ArrayList<>();
        List<Ingreso> ingresos = new ArrayList<>();
        Double totalIngresos = 0.0;
        Double totalDescuentos = 0.0;
        for (Empleado empleado: empleados) {
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

            ingresos.addAll(empleado.getIngresos());
            totalIngresos = ingresos.stream().mapToDouble(Ingreso::getMonto).sum();
            totalDescuentos = descuentos.stream().mapToDouble(Descuento::getMonto).sum();

            empleadoDTOS.add(
              EmpleadoDTO.builder()
                      .dui(empleado.getDui())
                      .nombres(empleado.getNombres())
                      .apellidos(empleado.getApellidos())
                      .salario(empleado.getSalario())
                      .totalIngresos(totalIngresos)
                      .totalDescuentos(totalDescuentos)
                      .build()
            );

        }
        model.addAttribute("empleados", empleadoDTOS);
        return "planilla";
    }
}
