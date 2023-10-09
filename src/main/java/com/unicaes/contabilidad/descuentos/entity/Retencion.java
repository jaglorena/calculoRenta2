package com.unicaes.contabilidad.descuentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Retencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private double desde;
    @Column
    private double hasta;
    @Column
    private double porcentaje;
    @Column
    private double exceso;
    @Column
    private double cuotaFija;
}
