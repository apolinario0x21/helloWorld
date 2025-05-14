package apolinario0x21.helloworld.service;

import apolinario0x21.helloworld.model.Viajante;
import apolinario0x21.helloworld.model.ViajanteLogin;
import apolinario0x21.helloworld.repository.ViajanteRepository;
import apolinario0x21.helloworld.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ViajanteService {

    @Autowired
    private ViajanteRepository viajanteRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<Viajante> cadastrarViajante(Viajante viajante) {

        if (viajanteRepository.findByEmail(viajante.getEmail()).isPresent())
            return Optional.empty();

        viajante.setSenha(criptografarSenha(viajante.getSenha()));

        return Optional.of(viajanteRepository.save(viajante));

    }

    public Optional<Viajante> atualizarViajante(Viajante viajante) {

        if (viajanteRepository.findById(viajante.getId()).isPresent()) {

            Optional<Viajante> buscaViajante = viajanteRepository.findByEmail(viajante.getEmail());

            if ((buscaViajante.isPresent()) && (buscaViajante.get().getId() != viajante.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado", null);

            viajante.setSenha(criptografarSenha(viajante.getSenha()));

            return Optional.ofNullable(viajanteRepository.save(viajante));
        }

        return Optional.empty();
    }

    public Optional<ViajanteLogin> autenticarViajante(Optional<ViajanteLogin> viajanteLogin) {

        var credenciais = new UsernamePasswordAuthenticationToken(viajanteLogin
                .get().getEmail(), viajanteLogin.get().getSenha());

        Authentication authentication = authenticationManager.authenticate(credenciais);

        if(authentication.isAuthenticated()) {

            Optional<Viajante> viajante = viajanteRepository.findByEmail(viajanteLogin.get().getEmail());

            if(viajante.isPresent()) {

                viajanteLogin.get().setId(viajante.get().getId());
                viajanteLogin.get().setNome(viajante.get().getNome());
                viajanteLogin.get().setFoto(viajante.get().getFoto());
                viajanteLogin.get().setToken(gerarToken(viajante.get().getEmail()));
                viajanteLogin.get().setSenha("");

                return viajanteLogin;
            }

        }

        return Optional.empty();
    }


    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }

    private String gerarToken(String viajante) {
        return "Bearer " + jwtService.generateToken(viajante);
    }
}
