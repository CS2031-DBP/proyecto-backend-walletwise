package com.example.walletwise.Subcategoria.domain;

@Service
public class SubcategoriaService {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    public SubcategoriaDTO crearSubcategoria(SubcategoriaDTO subcategoriaDTO) {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setNombre(subcategoriaDTO.getNombre());
        subcategoriaRepository.save(subcategoria);
        return mapToDTO(subcategoria);
    }

    private SubcategoriaDTO mapToDTO(Subcategoria subcategoria) {
        SubcategoriaDTO subcategoriaDTO = new SubcategoriaDTO();
        subcategoriaDTO.setId(subcategoria.getId());
        subcategoriaDTO.setNombre(subcategoria.getNombre());
        return subcategoriaDTO;
    }
}

