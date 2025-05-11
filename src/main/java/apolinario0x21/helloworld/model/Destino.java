package apolinario0x21.helloworld.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tb_destinos")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome não pode ser vazio")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destino", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("destino")
    private List<Resenha> resenha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Resenha> getResenha() {
        return resenha;
    }

    public void setResenha(List<Resenha> resenha) {
        this.resenha = resenha;
    }
}
