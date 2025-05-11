package apolinario0x21.helloworld.controller;

import apolinario0x21.helloworld.model.Resenha;
import apolinario0x21.helloworld.repository.DestinoRepository;
import apolinario0x21.helloworld.repository.ResenhaRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController // URL/Verbo (HTTP)/Request Body
@RequestMapping("/api/resenhas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ResenhaController {

    @Autowired
    private ResenhaRepository resenhaRepository;

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping // select * from tb_resenha;
    public ResponseEntity<List<Resenha>> getAll() {
        return ResponseEntity.ok(resenhaRepository.findAll());
    }

    @GetMapping({"/{id}"}) // select * from tb_resenha where id = id;
    public ResponseEntity<Resenha> getById(@PathVariable Long id) {
        return resenhaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/titulo/{titulo}") // select * from tb_resenha where titulo like %titulo%;
    public ResponseEntity<List<Resenha>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(resenhaRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

    @PostMapping
    public ResponseEntity<Resenha> post(@Valid @RequestBody Resenha resenha) {

        if(destinoRepository.existsById(resenha.getDestino().getId()))
            return ResponseEntity.status(HttpStatus.CREATED).body(resenhaRepository.save(resenha));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destino não encontrado", null);
    }

    @PutMapping
    public ResponseEntity<Resenha> put(@Valid @RequestBody Resenha resenha) {

        if(resenhaRepository.existsById(resenha.getId())) {

            if(destinoRepository.existsById(resenha.getDestino().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(resenhaRepository.save(resenha));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resenha não encontrada", null);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}") // delete from tb_resenha where id = id;
    public void delete(@PathVariable Long id) {
        Optional<Resenha> resenha = resenhaRepository.findById(id);

        if(resenha.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        resenhaRepository.deleteById(id);
    }
}
