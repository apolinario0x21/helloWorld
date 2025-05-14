package apolinario0x21.helloworld.repository;

import apolinario0x21.helloworld.model.Viajante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViajanteRepository extends JpaRepository<Viajante, Long> {
    /*
    Query method to find a Viajante by email
    select * from tb_viajantes where email = email;
     */
    public Optional<Viajante> findByEmail(String email);
}
