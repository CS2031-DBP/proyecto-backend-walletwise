package com.example.walletwise.Cuenta.domain;

import com.example.walletwise.Cuenta.dtos.CuentaDTO;
import com.example.walletwise.Cuenta.infrastructure.CuentaRepository;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(cuentaDTO.getNombre());
        cuenta.setSaldo(cuentaDTO.getSaldo());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setMoneda(cuentaDTO.getMoneda());

        // Asignar el Usuario a la Cuenta
        Usuario usuario = usuarioRepository.findById(cuentaDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + cuentaDTO.getUsuarioId()));
        cuenta.setUsuario(usuario);

        cuentaRepository.save(cuenta);
        return mapToDTO(cuenta);
    }

    public List<CuentaDTO> obtenerCuentasPorUsuarioId(Long usuarioId) {
        List<Cuenta> cuentas = cuentaRepository.findAll();  // Aquí debes implementar una búsqueda por usuarioId
        return cuentas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<CuentaDTO> obtenerTodasLasCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CuentaDTO obtenerCuentaPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
        return mapToDTO(cuenta);
    }

    public CuentaDTO actualizarCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));

        cuenta.setNombre(cuentaDTO.getNombre());
        cuenta.setSaldo(cuentaDTO.getSaldo());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setMoneda(cuentaDTO.getMoneda());

        cuentaRepository.save(cuenta);
        return mapToDTO(cuenta);
    }
    public void eliminarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
        cuentaRepository.delete(cuenta);
    }

    private CuentaDTO mapToDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setNombre(cuenta.getNombre());
        cuentaDTO.setSaldo(cuenta.getSaldo());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setMoneda(cuenta.getMoneda());
        cuentaDTO.setUsuarioId(cuenta.getUsuario().getId());  // Asignar el usuarioId
        return cuentaDTO;
    }
}
