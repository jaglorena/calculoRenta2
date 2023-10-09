package com.unicaes.contabilidad.descuentos.controller;

import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.service.DescuentoService;
import com.unicaes.contabilidad.descuentos.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/descuento")
public class DescuentoController {
    private EmpleadoService empleadoService;
    private DescuentoService descuentoService;
    @GetMapping
    public String descuento(Model model) {
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        model.addAttribute("empleados", empleados);
        return "descuento";

    }

    @PostMapping
    public String agregarDescuento(
            @RequestParam(value = "dui") String dui,
            @RequestParam(value = "descripcion") String descripcion,
            @RequestParam(value = "monto") Double monto,
            Model model
    ) {
        Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorDui(dui);
        if(empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();

            List<Descuento> descuentos = Optional.ofNullable(empleado.getDescuentos())
                    .orElse(Collections.emptyList());
            double descuentoMaximo = empleado.getSalario() * 0.3;
            Double totalDescuentos = 0.0;
            for (Descuento descuento:descuentos) {
                totalDescuentos += descuento.getMonto();
            }
            if(monto + totalDescuentos < descuentoMaximo) {
                Descuento descuento = Descuento.builder()
                        .dui(empleado.getDui())
                        .descripcion(descripcion)
                        .monto(monto)
                        .build();

                descuento = descuentoService.guardarDescuento(descuento);
                descuentos.add(descuento);
                empleado.setDescuentos(descuentos);

                empleadoService.guardarEmpleado(empleado);
                model.addAttribute("mensaje", "Ingreso guardado con éxito");
            }
        }
        return "descuento";
    }
}
