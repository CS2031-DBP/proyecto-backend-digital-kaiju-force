package com.dkf.ODAD.Usuario.domain;

import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Usuario.infrastructure.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private BaseUserRepository<Usuario> userRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    public Usuario findByEmail(String username, String role) {
        Usuario user;
        if (role.equals("ROLE_MEDICO"))
            user = medicoRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado!"));
        else
            user = pacienteRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado!"));

        return user;
    }
    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) usuario;
        };
    }
}
