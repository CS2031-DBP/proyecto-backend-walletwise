package com.example.walletwise.Reporte.domain;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public ReporteDTO crearReporte(ReporteDTO reporteDTO) {
        Reporte reporte = new Reporte();
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setTipoReporte(reporteDTO.getTipoReporte());
        reporte.setContenido(reporteDTO.getContenido());
        reporte.setRangoFechas(reporteDTO.getRangoFechas());
        reporte.setFormato(reporteDTO.getFormato());
        reporteRepository.save(reporte);
        return mapToDTO(reporte);
    }

    private ReporteDTO mapToDTO(Reporte reporte) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setId(reporte.getId());
        reporteDTO.setTipoReporte(reporte.getTipoReporte());
        reporteDTO.setContenido(reporte.getContenido());
        reporteDTO.setRangoFechas(reporte.getRangoFechas());
        reporteDTO.setFormato(reporte.getFormato());
        return reporteDTO;
    }
}

