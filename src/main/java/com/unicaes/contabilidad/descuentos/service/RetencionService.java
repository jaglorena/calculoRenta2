package com.unicaes.contabilidad.descuentos.service;

import com.unicaes.contabilidad.descuentos.entity.Retencion;
import com.unicaes.contabilidad.descuentos.repository.RetencionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RetencionService {
    private RetencionRepository retencionRepository;

    public Retencion guardarRetencion(Retencion retencion) {
        return retencionRepository.save(retencion);
    }

    public boolean primeraCarga() {
        List<Retencion> retenciones = (List<Retencion>) retencionRepository.findAll();
        return retenciones.isEmpty();
    }

    public Retencion obtenerRetencionPorSalario(double salario) {
        return retencionRepository.obtenerTramo(salario);
    }
}
