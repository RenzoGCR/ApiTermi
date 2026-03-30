package org.beginsecure.apitermi.services;

import org.beginsecure.apitermi.entities.Usuario;
import org.beginsecure.apitermi.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetallesUsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public DetallesUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en nuestra base de datos (MongoAtlas)
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Lo convertimos al formato UserDetails que entiende Spring Security
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // Spring comprobará esta contraseña hasheada
                .roles(usuario.getRoles().replace("ROLE_", "")) // Spring añade "ROLE_" automáticamente, así que se lo quitamos aquí
                .build();
    }
}