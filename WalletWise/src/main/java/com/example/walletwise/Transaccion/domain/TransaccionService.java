package com.example.walletwise.Transaccion.domain;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    public TransaccionDTO crearTransaccion(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setDestinatario(transaccionDTO.getDestinatario());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setTipo(transaccionDTO.getTipo());
        transaccionRepository.save(transaccion);
        return mapToDTO(transaccion);
    }

    private TransaccionDTO mapToDTO(Transaccion transaccion) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setId(transaccion.getId());
        transaccionDTO.setMonto(transaccion.getMonto());
        transaccionDTO.setDestinatario(transaccion.getDestinatario());
        transaccionDTO.setFecha(transaccion.getFecha());
        transaccionDTO.setTipo(transaccion.getTipo());
        return transaccionDTO;
    }
}

