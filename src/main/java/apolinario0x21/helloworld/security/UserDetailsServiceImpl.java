package apolinario0x21.helloworld.security;

import apolinario0x21.helloworld.model.Viajante;
import apolinario0x21.helloworld.repository.ViajanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ViajanteRepository viajanteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Viajante> viajante = viajanteRepository.findByEmail(username);

        if(viajante.isPresent())
            return new UserDetailsImpl(viajante.get());
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "E-mail não encontrado");
    }
}
