package com.unicaes.contabilidad.descuentos.controller;

import com.unicaes.contabilidad.descuentos.entity.Retencion;
import com.unicaes.contabilidad.descuentos.service.RetencionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
@AllArgsConstructor
public class InicioController {

    private RetencionService retencionService;
    @GetMapping("/")
    public String inicio(){
        if (retencionService.primeraCarga()) {
            // Guardando los tramos de la tabla
            //Tramo I
            retencionService.guardarRetencion(
                    Retencion.builder()
                            .desde(0.01)
                            .hasta(472.0)
                            .porcentaje(0.0)
                            .exceso(0.0)
                            .cuotaFija(0.0)
                            .build()
            );
            //Tramo II
            retencionService.guardarRetencion(
                    Retencion.builder()
                            .desde(472.01)
                            .hasta(895.24)
                            .porcentaje(0.10)
                            .exceso(472.0)
                            .cuotaFija(17.67)
                            .build()
            );
            //Tramo III
            retencionService.guardarRetencion(
                    Retencion.builder()
                            .desde(895.25)
                            .hasta(2038.10)
                            .porcentaje(0.20)
                            .exceso(895.24)
                            .cuotaFija(60.0)
                            .build()
            );
            //Tramo IV
            retencionService.guardarRetencion(
                    Retencion.builder()
                            .desde(2038.11)
                            .hasta(Double.MAX_VALUE)
                            .porcentaje(0.30)
                            .exceso(2038.10)
                            .cuotaFija(288.57)
                            .build()
            );
        }
        return "inicio";
    }
}
