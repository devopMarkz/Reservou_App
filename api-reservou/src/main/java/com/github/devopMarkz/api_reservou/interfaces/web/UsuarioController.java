package com.github.devopMarkz.api_reservou.interfaces.web;

import com.github.devopMarkz.api_reservou.application.usuario.service.UsuarioService;
import com.github.devopMarkz.api_reservou.interfaces.dto.usuario.UsuarioRequestDTO;
import com.github.devopMarkz.api_reservou.interfaces.dto.usuario.UsuarioResponseDTO;
import com.github.devopMarkz.api_reservou.utils.GerenciadorDePermissoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.devopMarkz.api_reservou.utils.GeradorDeUri.generateUri;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioRequestDTO requestDTO){
        Long id = usuarioService.criarUsuario(requestDTO);
        URI uri = generateUri(id);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){
        UsuarioResponseDTO responseDTO = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    public ResponseEntity<Page<UsuarioResponseDTO>> buscarComFiltros(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "perfil", required = false) String perfil,
            @RequestParam(name = "ativo", required = false) Boolean ativo,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuarios(nome, email, perfil, ativo, pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO requestDTO){
        usuarioService.atualizarUsuario(id, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    @Deprecated(forRemoval = true)
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desativar")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    public ResponseEntity<Void> desativarUsuario(@PathVariable Long id){
        usuarioService.desativarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
