package com.unicaes.contabilidad.descuentos.controller;


import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.service.EmpleadoService;
import com.unicaes.contabilidad.descuentos.service.IngresoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/ingreso")
public class IngresoController {

    private EmpleadoService empleadoService;
    private IngresoService ingresoService;
    @GetMapping
    public String ingreso(Model model){
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        model.addAttribute("empleados", empleados);
        return "ingreso";
    }

    @PostMapping
    public String agregarIngreso(
            @RequestParam(name = "dui") String dui,
            @RequestParam(value = "descripcion") String descripcion,
            @RequestParam(value = "monto") Double monto,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorDui(dui);
        if(empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            List<Ingreso> ingresos = Optional.ofNullable(empleado.getIngresos()).orElse(Collections.emptyList());
            Ingreso ingreso = Ingreso.builder()
                    .dui(empleado.getDui())
                    .descripcion(descripcion)
                    .monto(monto)
                    .build();
            ingreso = ingresoService.guardarIngreso(ingreso);
            ingresos.add(ingreso);
            empleado.setIngresos(ingresos);
            empleadoService.guardarEmpleado(empleado);
            redirectAttributes
                    .addFlashAttribute("mensaje", "Ingreso registrado correctamente")
                    .addFlashAttribute("clase", "success");

        }

        return "redirect:/ingreso";
    }
}