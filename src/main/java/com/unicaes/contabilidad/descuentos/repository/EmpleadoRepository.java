package com.unicaes.contabilidad.descuentos.repository;

import com.unicaes.contabilidad.descuentos.entity.Empleado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, String> {

    List<Empleado> getEmpleadosByNombresContainingAndApellidosContaining(String nombre, String apellido);
}
