package com.unicaes.contabilidad.descuentos.repository;

import com.unicaes.contabilidad.descuentos.entity.Descuento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DescuentoRepository extends CrudRepository<Descuento, Integer> {
    List<Descuento> getDescuentoByDui(String dui);
}
