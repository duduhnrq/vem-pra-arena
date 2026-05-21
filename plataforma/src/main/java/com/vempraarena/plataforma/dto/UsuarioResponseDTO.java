package com.vempraarena.plataforma.dto;

import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.utils.DataMaskingUtils;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        String cpf,
        String telefone,
        LocalDate dataNascimento
) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                DataMaskingUtils.maskEmail(usuario.getEmail()),
                DataMaskingUtils.maskCpf(usuario.getCpf()),
                usuario.getTelefone(), 
                usuario.getDataNascimento()
        );
    }
}
