package com.unicaes.contabilidad.descuentos.repository;

import com.unicaes.contabilidad.descuentos.entity.Retencion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RetencionRepository extends CrudRepository<Retencion, Integer> {

    @Query("From Retencion where :salario between desde and hasta")
    public Retencion obtenerTramo(double salario);
}
