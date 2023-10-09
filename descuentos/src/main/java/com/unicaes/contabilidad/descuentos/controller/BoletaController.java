package com.unicaes.contabilidad.descuentos.controller;

import com.unicaes.contabilidad.descuentos.comunes.Calculos;
import com.unicaes.contabilidad.descuentos.dto.BoletaDTO;
import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.service.EmpleadoService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/boleta")
public class BoletaController {
    private EmpleadoService empleadoService;
    private Calculos calculos;
    @GetMapping
    public String boleta() { return "boleta";}

    @GetMapping("/{dui}")
    public String consultarBoleta(@PathVariable String dui, Model model) {
        Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorDui(dui);
        BoletaDTO boletaDTO = new BoletaDTO();
        if (empleadoOptional.isPresent()) {
            double totalIngresos;
            double totalDescuentos;
            double totalRetenciones;
            double totalIndemnizacion;
            List<Descuento> retenciones = new ArrayList<>();
            List<Ingreso> ingresos = new ArrayList<>();
            List<Ingreso> indemnizaciones = new ArrayList<>();

            Empleado empleado = empleadoOptional.get();

            //Llenando las listas necesarias
            retenciones.add(
                    calculos.descuentoIsss(empleado)
            );
            retenciones.add(
                    calculos.descuentoAFP(empleado)
            );
            retenciones.add(
                    calculos.descuentoISR(empleado)
            );
            //Otros ingresos
            ingresos.add(
              Ingreso.builder()
                      .id(0)
                      .descripcion("Salario")
                      .monto(empleado.getSalario())
                      .build()
            );

            indemnizaciones.add(
                    calculos.calculoVacacion(empleado)
            );

            indemnizaciones.add(
                    calculos.calculoAguinaldo(empleado)
            );

            ingresos.addAll(empleado.getIngresos());

            YearMonth yearMonth = YearMonth.of(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
            String periodo = "Boleta de pago: desde " + yearMonth.atDay(1) + " hasta " + yearMonth.atEndOfMonth();
            totalIngresos = ingresos.stream().mapToDouble(Ingreso::getMonto).sum();
            totalDescuentos = empleado.getDescuentos().stream().mapToDouble(Descuento::getMonto).sum();
            totalRetenciones = retenciones.stream().mapToDouble(Descuento::getMonto).sum();
            totalIndemnizacion = indemnizaciones.stream().mapToDouble(Ingreso::getMonto).sum();

            //Creando objeto DTO para mostrar informacion en html
            boletaDTO.setNombreCompleto(String.format("%s %s", empleado.getNombres(), empleado.getApellidos()).trim());
            boletaDTO.setCargo(empleado.getCargo());
            boletaDTO.setPeriodo(periodo);
            boletaDTO.setIngresos(ingresos);
            boletaDTO.setDescuentos(empleado.getDescuentos());
            boletaDTO.setRetenciones(retenciones);
            boletaDTO.setTotalIngresos(Optional.of(totalIngresos).orElse(0.0));
            boletaDTO.setTotalDescuentos(Optional.of(totalDescuentos).orElse(0.0));
            boletaDTO.setTotalRetenciones(Optional.of(totalRetenciones).orElse(0.0));
            boletaDTO.setTotalAPagar(Optional.of(totalIngresos - totalDescuentos - totalRetenciones + totalIndemnizacion).orElse(0.0));
            boletaDTO.setIndemnizaciones(indemnizaciones);
            boletaDTO.setTotalIndemnizacion(totalIndemnizacion);
            boletaDTO.setDui(empleado.getDui());
        }

        model.addAttribute("boletaDto", boletaDTO);
        return "boleta";
    }
}
