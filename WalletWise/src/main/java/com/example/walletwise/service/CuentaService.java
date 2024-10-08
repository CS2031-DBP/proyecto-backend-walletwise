package com.example.walletwise.service;

import com.example.walletwise.dto.CuentaDTO;
import com.example.walletwise.infrastructure.CuentaRepository;
import com.example.walletwise.domain.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setBanco(cuentaDTO.getBanco());
        cuenta.setSaldo(cuentaDTO.getSaldo());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setMoneda(cuentaDTO.getMoneda());
        cuentaRepository.save(cuenta);
        return mapToDTO(cuenta);
    }

    public List<CuentaDTO> obtenerCuentasPorUsuarioId(Long usuarioId) {
        List<Cuenta> cuentas = cuentaRepository.findAll();  // Aquí debes implementar una búsqueda por usuarioId
        return cuentas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CuentaDTO mapToDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setBanco(cuenta.getBanco());
        cuentaDTO.setSaldo(cuenta.getSaldo());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setMoneda(cuenta.getMoneda());
        return cuentaDTO;
    }
}
