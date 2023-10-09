package com.unicaes.contabilidad.descuentos.service;

import com.unicaes.contabilidad.descuentos.entity.Empleado;
import com.unicaes.contabilidad.descuentos.repository.EmpleadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpleadoService {
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerEmpleados(){
        return (List<Empleado>) empleadoRepository.findAll();
    }

    public Empleado guardarEmpleado(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public Optional<Empleado> obtenerEmpleadoPorDui(String dui){
        return empleadoRepository.findById(dui);
    }

    public List<Empleado> obtenerEmpleadoProNombreOApellido(String nombre, String appelido) {
        return empleadoRepository.getEmpleadosByNombresContainingAndApellidosContaining(nombre, appelido);
    }
}
