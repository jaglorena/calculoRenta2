package com.unicaes.contabilidad.descuentos.controller;

import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.service.EmpleadoService;
import com.unicaes.contabilidad.descuentos.service.IngresoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping (value = "/empleado")
public class EmpleadoController {

    private EmpleadoService empleadoService;

    @GetMapping
    public String empleado() { return "empleado";}

    @PostMapping
    public String crearEmpleado(
            @RequestParam(value = "dui") String dui,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "salario") Double salario,
            @RequestParam(value = "fechaIngreso") LocalDate fechaIngreso,
            @RequestParam(value = "fechaSalida", required = false) LocalDate fechaSalida,
            @RequestParam(value = "fechaNacimiento") LocalDate fechaNacimiento,
            Model modelo
            ) {

        Empleado empleado = Empleado.builder()
                .dui(dui)
                .nombres(nombre)
                .apellidos(apellido)
                .cargo(cargo)
                .salario(salario)
                .fechaNacimiento(fechaNacimiento)
                .fechaSalida(fechaSalida)
                .fechaIngreso(fechaIngreso)
                .build();
        empleadoService.guardarEmpleado(empleado);
        modelo.addAttribute("mensaje", "exito");
        return "empleado";
    }
}
