package com.github.devopMarkz.api_reservou.usuario.interfaces.controller;

import com.github.devopMarkz.api_reservou.usuario.application.UsuarioService;
import com.github.devopMarkz.api_reservou.usuario.interfaces.dto.UsuarioRequestDTO;
import com.github.devopMarkz.api_reservou.usuario.interfaces.dto.UsuarioResponseDTO;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.devopMarkz.api_reservou.shared.utils.GeradorDeUri.generateUri;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Endpoints de Usuário", description = "Endpoints responsáveis pela criação, busca, atualização, desativação e exclusão de usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(
            summary = "Criação de Usuário",
            description = "Este endpoint permite a criação de um novo usuário com base nas informações fornecidas.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário criado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string", description = "URI do recurso recém-criado")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Violação de unicidade de chave. O e-mail fornecido já está em uso."
                    )
            }
    )
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UsuarioRequestDTO requestDTO){
        Long id = usuarioService.criarUsuario(requestDTO);
        URI uri = generateUri(id);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    @Operation(
            summary = "Buscar Usuário por ID",
            description = "Este endpoint retorna as informações detalhadas de um usuário com base no seu ID. Apenas administradores têm permissão para acessar essa informação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado."
                    )
            }
    )
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){
        UsuarioResponseDTO responseDTO = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    @Operation(
            summary = "Buscar Usuários com Filtros",
            description = "Este endpoint permite a busca de usuários com filtros como nome, e-mail, perfil e status ativo. A consulta pode ser paginada.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuários encontrados com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PagedModel.class, contentSchema = UsuarioResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos fornecidos para a consulta."
                    )
            }
    )
    public ResponseEntity<PagedModel<UsuarioResponseDTO>> buscarComFiltros(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "perfil", required = false) String perfil,
            @RequestParam(name = "ativo", required = false) Boolean ativo,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        PagedModel<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuarios(nome, email, perfil, ativo, pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar Usuário",
            description = "Este endpoint permite a atualização dos dados de um usuário existente. O usuário será identificado pelo seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário atualizado com sucesso."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado."
                    )
            }
    )
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO requestDTO){
        usuarioService.atualizarUsuario(id, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    @Deprecated(forRemoval = true)
    @Operation(
            summary = "Deletar Usuário",
            description = "Este endpoint permite a exclusão de um usuário, mas está obsoleto e será removido em versões futuras. Apenas administradores podem realizar esta ação.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário deletado com sucesso."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado."
                    )
            }
    )
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_ADMINISTRADOR)
    @Operation(
            summary = "Desativar Usuário",
            description = "Este endpoint permite desativar um usuário, marcando-o como inativo. Apenas administradores podem realizar esta ação.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário desativado com sucesso."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado."
                    )
            }
    )
    public ResponseEntity<Void> desativarUsuario(@PathVariable Long id){
        usuarioService.desativarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
