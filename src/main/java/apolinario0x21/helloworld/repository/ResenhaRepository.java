package apolinario0x21.helloworld.repository;

import apolinario0x21.helloworld.model.Resenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResenhaRepository extends JpaRepository<Resenha, Long> {
    // Query Methods - select * from tb_resenha where titulo like %titulo%
    List<Resenha> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}
