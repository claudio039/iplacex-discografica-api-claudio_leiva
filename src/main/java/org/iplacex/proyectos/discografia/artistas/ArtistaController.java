
package org.iplacex.proyectos.discografia.artistas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        try {
            Artista nuevoArtista = artistaRepository.save(artista);

            return new ResponseEntity<Object>(
                nuevoArtista,
                HttpStatus.CREATED
            );

        } catch (Exception e) {

            return new ResponseEntity<Object>(
                "Error al insertar el artista",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {

        List<Artista> artistas = artistaRepository.findAll();

        return new ResponseEntity<List<Artista>>(
            artistas,
            HttpStatus.OK
        );
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(
            @PathVariable String id) {

        return artistaRepository.findById(id)
            .<ResponseEntity<Object>>map(artista ->
                new ResponseEntity<Object>(
                    artista,
                    HttpStatus.OK
                )
            )
            .orElseGet(() ->
                new ResponseEntity<Object>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                )
            );
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        try {
            if (!artistaRepository.existsById(id)) {
                return new ResponseEntity<Object>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            artista._id = id;

            Artista artistaActualizado =
                artistaRepository.save(artista);

            return new ResponseEntity<Object>(
                artistaActualizado,
                HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<Object>(
                "Error al actualizar el artista",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        try {
            if (!artistaRepository.existsById(id)) {
                return new ResponseEntity<Object>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            artistaRepository.deleteById(id);

            return new ResponseEntity<Object>(
                "Artista eliminado correctamente",
                HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<Object>(
                "Error al eliminar el artista",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
