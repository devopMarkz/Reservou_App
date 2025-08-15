package com.github.devopMarkz.api_reservou.interfaces.web;

import com.github.devopMarkz.api_reservou.application.estabelecimento.EstabelecimentoService;
import com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento.EstabelecimentoRequestDTO;
import com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento.EstabelecimentoResponseDTO;
import com.github.devopMarkz.api_reservou.utils.GerenciadorDePermissoes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.devopMarkz.api_reservou.utils.GeradorDeUri.generateUri;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @PostMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> criarEstabelecimento(@Valid @RequestBody EstabelecimentoRequestDTO requestDTO) {
        Long id = estabelecimentoService.criarEstabelecimento(requestDTO);
        URI uri = generateUri(id);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<EstabelecimentoResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        EstabelecimentoResponseDTO responseDTO = estabelecimentoService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/admin")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Page<EstabelecimentoResponseDTO>> buscarParaDonos(
        @RequestParam(name = "nome", required = false) String nome,
        @RequestParam(name = "ativo", required = false) Boolean ativo,
        @RequestParam(name = "logradouro", required = false) String logradouro,
        @RequestParam(name = "numero", required = false) String numero,
        @RequestParam(name = "complemento", required = false) String complemento,
        @RequestParam(name = "bairro", required = false) String bairro,
        @RequestParam(name = "cidade", required = false) String cidade,
        @RequestParam(name = "estado", required = false) String estado,
        @RequestParam(name = "cep", required = false) String cep,
        @RequestParam(name = "latitude", required = false) String latitude,
        @RequestParam(name = "longitude", required = false) String longitude,
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EstabelecimentoResponseDTO> responseDTOS = estabelecimentoService.buscarEstabelecimentosParaDono(nome, ativo, logradouro, numero, complemento, bairro, cidade, estado, cep, pageNumber, pageSize);
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/public")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Page<EstabelecimentoResponseDTO>> buscarParaUsuarios(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "ativo", required = false) Boolean ativo,
            @RequestParam(name = "logradouro", required = false) String logradouro,
            @RequestParam(name = "numero", required = false) String numero,
            @RequestParam(name = "complemento", required = false) String complemento,
            @RequestParam(name = "bairro", required = false) String bairro,
            @RequestParam(name = "cidade", required = false) String cidade,
            @RequestParam(name = "estado", required = false) String estado,
            @RequestParam(name = "cep", required = false) String cep,
            @RequestParam(name = "latitude", required = false) String latitude,
            @RequestParam(name = "longitude", required = false) String longitude,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EstabelecimentoResponseDTO> responseDTOS = estabelecimentoService.buscarEstabelecimentosParaUsuarios(nome, ativo, logradouro, numero, complemento, bairro, cidade, estado, cep, pageNumber, pageSize);
        return ResponseEntity.ok(responseDTOS);
    }

    @PutMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> atualizarEstabelecimento(@PathVariable("id") Long id, @Valid @RequestBody EstabelecimentoRequestDTO estabelecimentoRequestDTO) {
        estabelecimentoService.atualizarEstabelecimento(id, estabelecimentoRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> desativarEstabelecimento(@PathVariable("id") Long id){
        estabelecimentoService.desativarEstabelecimento(id);
        return ResponseEntity.noContent().build();
    }

}
