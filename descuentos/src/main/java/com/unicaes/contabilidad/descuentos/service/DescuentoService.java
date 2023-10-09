package com.unicaes.contabilidad.descuentos.service;

import com.unicaes.contabilidad.descuentos.entity.Descuento;
import com.unicaes.contabilidad.descuentos.repository.DescuentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DescuentoService {
    private DescuentoRepository descuentoRepository;

    public List<Descuento> obtenerDescuentos(String dui) {
        return descuentoRepository.getDescuentoByDui(dui);
    }
    public Descuento guardarDescuento(Descuento descuento) {
        return descuentoRepository.save(descuento);
    }
}
