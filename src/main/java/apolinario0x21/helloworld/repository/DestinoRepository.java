package apolinario0x21.helloworld.repository;

import apolinario0x21.helloworld.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long>  {
    // Query Methods - SQL equivalente: select * from tb_destinos where descricao like %descricao%
    public List<Destino> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
}
