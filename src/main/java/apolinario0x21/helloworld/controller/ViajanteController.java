package apolinario0x21.helloworld.controller;

import apolinario0x21.helloworld.model.Viajante;
import apolinario0x21.helloworld.model.ViajanteLogin;
import apolinario0x21.helloworld.repository.ViajanteRepository;
import apolinario0x21.helloworld.service.ViajanteService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viajantes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ViajanteController {

    @Autowired
    private ViajanteService viajanteService;

    @Autowired
    private ViajanteRepository viajanteRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Viajante>> getAll() {

        return ResponseEntity.ok(viajanteRepository.findAll());
    }

    @PostMapping("/logar")
    public ResponseEntity<ViajanteLogin> autenticarViajante(@RequestBody Optional<ViajanteLogin> viajanteLogin) {

        return viajanteService.autenticarViajante(viajanteLogin)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Viajante> postViajante(@RequestBody @Valid Viajante viajante) {

        return viajanteService.cadastrarViajante(viajante)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Viajante> putViajante(@RequestBody @Valid Viajante viajante) {

        return viajanteService.atualizarViajante(viajante)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
