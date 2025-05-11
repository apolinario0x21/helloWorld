package apolinario0x21.helloworld.controller;

import apolinario0x21.helloworld.model.Destino;
import apolinario0x21.helloworld.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/destinos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DestinoController {

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping
    public ResponseEntity<List<Destino>> getAll() {
        return ResponseEntity.ok(destinoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> getById(@PathVariable Long id) {
        return destinoRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Destino>> getByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(destinoRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }

    @PostMapping
    public ResponseEntity<Destino> post(@RequestBody Destino destino) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(destinoRepository.save(destino));
    }

    @PutMapping
    public ResponseEntity<Destino> put(@RequestBody Destino destino) {
        return destinoRepository.findById(destino.getId())
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(destinoRepository.save(destino)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Destino> destino = destinoRepository.findById(id);

        if (destino.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        destinoRepository.deleteById(id);
    }
}
