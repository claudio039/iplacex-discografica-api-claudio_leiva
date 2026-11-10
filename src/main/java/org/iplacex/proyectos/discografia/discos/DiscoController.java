
package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepository;
    private final IArtistaRepository artistaRepository;

    public DiscoController(
            IDiscoRepository discoRepository,
            IArtistaRepository artistaRepository) {

        this.discoRepository = discoRepository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(
            @RequestBody Disco disco) {

        try {
            if (disco.idArtista == null ||
                !artistaRepository.existsById(disco.idArtista)) {

                return new ResponseEntity<Object>(
                    "El artista no existe",
                    HttpStatus.NOT_FOUND
                );
            }

            Disco nuevoDisco = discoRepository.save(disco);

            return new ResponseEntity<Object>(
                nuevoDisco,
                HttpStatus.CREATED
            );

        } catch (Exception e) {
            return new ResponseEntity<Object>(
                "Error al insertar el disco",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        List<Disco> discos = discoRepository.findAll();

        return new ResponseEntity<List<Disco>>(
            discos,
            HttpStatus.OK
        );
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(
            @PathVariable String id) {

        return discoRepository.findById(id)
            .<ResponseEntity<Object>>map(disco ->
                new ResponseEntity<Object>(
                    disco,
                    HttpStatus.OK
                )
            )
            .orElseGet(() ->
                new ResponseEntity<Object>(
                    "Disco no encontrado",
                    HttpStatus.NOT_FOUND
                )
            );
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscosByArtistaRequest(
            @PathVariable String id) {

        try {
            if (!artistaRepository.existsById(id)) {
                return new ResponseEntity<Object>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            List<Disco> discos =
                discoRepository.findDiscosByIdArtista(id);

            return new ResponseEntity<Object>(
                discos,
                HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<Object>(
                "Error al consultar los discos del artista",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
