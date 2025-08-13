package com.github.devopMarkz.api_reservou.application.usuario.service;

import com.github.devopMarkz.api_reservou.application.usuario.mapper.UsuarioMapper;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import com.github.devopMarkz.api_reservou.domain.repository.usuario.UsuarioRepository;
import com.github.devopMarkz.api_reservou.infraestructure.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.infraestructure.exception.ViolacaoUnicidadeChaveException;
import com.github.devopMarkz.api_reservou.interfaces.dto.usuario.UsuarioRequestDTO;
import com.github.devopMarkz.api_reservou.interfaces.dto.usuario.UsuarioResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long criarUsuario(UsuarioRequestDTO requestDTO){
        Usuario usuario = usuarioMapper.toUsuario(requestDTO);
        usuario.setSenha(passwordEncoder.encode(requestDTO.getSenha()));
        usuarioRepository.save(usuario);
        return usuario.getId();
    }

    @Transactional(readOnly = true)
    public PagedModel<UsuarioResponseDTO> buscarUsuarios(String nome, String email, String perfil, Boolean ativo, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findByFilters(nome, email, perfil, ativo, pageable);
        return new PagedModel<>(usuarios.map(usuarioMapper::toUsuarioResponseDTO));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntidadeInexistenteException("Usuário não encontrado."));
        return usuarioMapper.toUsuarioResponseDTO(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizarUsuario(Long id, UsuarioRequestDTO requestDTO){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeInexistenteException("Usuário não encontrado."));

        validarViolacaoDeFK(requestDTO.getEmail());

        Usuario usuarioAtualizado = usuarioMapper.toUsuario(requestDTO);
        usuarioAtualizado.setSenha(passwordEncoder.encode(requestDTO.getSenha()));

        BeanUtils.copyProperties(usuarioAtualizado, usuario, "id", "perfil", "ativo", "refreshTokenJti", "plano");

        usuarioRepository.save(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletarUsuario(Long id){
        if(!usuarioRepository.existsById(id)) {
            throw new EntidadeInexistenteException("Usuário inexistente.");
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void desativarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeInexistenteException("Usuário inexistente."));

        usuario.desativar();

        usuarioRepository.save(usuario);
    }

    private void validarViolacaoDeFK(String email) {
        if(usuarioRepository.existsByEmail(email)) {
            throw new ViolacaoUnicidadeChaveException(email + " já está sendo utilizado.");
        }
    }

}
