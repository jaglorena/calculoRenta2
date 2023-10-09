package com.unicaes.contabilidad.descuentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Empleado {
    @Id
    private String dui;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column
    private String cargo;
    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;
    @Column
    private Double salario;
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @OneToMany
    @JoinColumn(name = "dui", referencedColumnName = "dui")
    List<Ingreso> ingresos;

    @OneToMany
    @JoinColumn(name = "dui", referencedColumnName = "dui")
    List<Descuento> descuentos;
}
