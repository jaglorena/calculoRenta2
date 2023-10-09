package com.unicaes.contabilidad.descuentos.service;

import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import com.unicaes.contabilidad.descuentos.repository.IngresoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngresoService {

    private IngresoRepository ingresoRepository;

    public List<Ingreso> obtenerIngresos(String dui) {
        return ingresoRepository.getIngresoByDui(dui);
    }

    public Ingreso guardarIngreso(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }
}
