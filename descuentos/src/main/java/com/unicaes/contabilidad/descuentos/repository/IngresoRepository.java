package com.unicaes.contabilidad.descuentos.repository;

import com.unicaes.contabilidad.descuentos.entity.Ingreso;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngresoRepository extends CrudRepository<Ingreso, Integer> {
    List<Ingreso> getIngresoByDui(String dui);
}
