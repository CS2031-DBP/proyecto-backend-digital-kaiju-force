package com.dkf.ODAD.auth;


import com.dkf.ODAD.Event.emailEvent;
import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Usuario.domain.Role;
import com.dkf.ODAD.Usuario.domain.Usuario;
import com.dkf.ODAD.Usuario.infrastructure.BaseUserRepository;
import com.dkf.ODAD.auth.dto.AuthJwtResponse;
import com.dkf.ODAD.auth.dto.AuthLoginRequest;
import com.dkf.ODAD.auth.dto.AuthRegisterRequest;
import com.dkf.ODAD.config.JwtService;
import com.dkf.ODAD.exceptions.UserAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthService {

    final BaseUserRepository<Usuario> userRepository;
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;
    final ModelMapper modelMapper;
    final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthService(BaseUserRepository<Usuario> userRepository, JwtService jwtService,
                       PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                       ApplicationEventPublisher applicationEventPublisher){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public AuthJwtResponse login(AuthLoginRequest req){
        Optional<Usuario> user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered.");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect.");

        AuthJwtResponse response = new AuthJwtResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public AuthJwtResponse register(AuthRegisterRequest req){
        Optional<Usuario> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistsException("Email is already registered.");

        boolean isMedico = req.getIsMedico();



        if (isMedico) {
            Medico newMedico = new Medico();
            newMedico.setNombre(req.getNombre());
            newMedico.setApellido(req.getApellido());
            newMedico.setTelefono(req.getTelefono());
            newMedico.setEmail(req.getEmail());
            newMedico.setPassword(passwordEncoder.encode(req.getPassword()));
            newMedico.setCreatedAt(ZonedDateTime.now());
            newMedico.setRole(Role.MEDICO);
            userRepository.save(newMedico);

            AuthJwtResponse response = new AuthJwtResponse();
            response.setToken(jwtService.generateToken(newMedico));
            return response;
        }
        else {
            Paciente newPassenger = new Paciente();
            newPassenger.setNombre(req.getNombre());
            newPassenger.setApellido(req.getApellido());
            newPassenger.setTelefono(req.getTelefono());
            newPassenger.setEmail(req.getEmail());
            newPassenger.setPassword(passwordEncoder.encode(req.getPassword()));
            newPassenger.setCreatedAt(ZonedDateTime.now());
            newPassenger.setRole(Role.PACIENTE);
            userRepository.save(newPassenger);

            AuthJwtResponse response = new AuthJwtResponse();
            response.setToken(jwtService.generateToken(newPassenger));
            applicationEventPublisher.publishEvent(new emailEvent(newPassenger.getEmail()));
            return response;
        }
    }

    public AuthJwtResponse registerAdmin(AuthRegisterRequest req) {
        Optional<Usuario> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistsException("Email is already registered.");

        Usuario newAdmin = new Usuario();
        newAdmin.setNombre(req.getNombre());
        newAdmin.setApellido(req.getApellido());
        newAdmin.setTelefono(req.getTelefono());
        newAdmin.setEmail(req.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(req.getPassword()));
        newAdmin.setCreatedAt(ZonedDateTime.now());
        newAdmin.setRole(Role.ADMIN);

        userRepository.save(newAdmin);

        AuthJwtResponse response = new AuthJwtResponse();
        response.setToken(jwtService.generateToken(newAdmin));
        return response;
    }

}
