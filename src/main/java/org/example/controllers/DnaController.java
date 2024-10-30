package org.example.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.services.DnaService;
import org.example.dto.DnaResponse;
import org.example.dto.DnaRequest;

@RestController
@RequestMapping("/mutant")
public class DnaController {
    private final DnaService dnaService;

    public DnaController(DnaService dnaService) {
        this.dnaService = dnaService;
    }

    @PostMapping
    public ResponseEntity<DnaResponse> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = dnaService.analyzeDna(dnaRequest.getDna());
        DnaResponse dnaResponse = new DnaResponse(isMutant);
        if (isMutant) {
            return ResponseEntity.ok(dnaResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dnaResponse);
        }
    }
}

/*package org.example.controllers;

// Definición del paquete donde se encuentra esta clase

import jakarta.validation.Valid; // Importa la anotación @Valid para validar objetos de entrada
import org.springframework.http.HttpStatus; // Para utilizar códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Para manejar la respuesta HTTP de forma flexible
import org.springframework.web.bind.annotation.PostMapping; // Indica que el método responderá a solicitudes POST
import org.springframework.web.bind.annotation.RequestBody; // Indica que el cuerpo de la petición HTTP será mapeado a un objeto
import org.springframework.web.bind.annotation.RequestMapping; // Permite definir rutas para el controlador
import org.springframework.web.bind.annotation.RestController; // Indica que esta clase es un controlador REST
import org.example.services.DnaService; // Importa el servicio que analizará la secuencia de ADN
import org.example.dto.DnaResponse; // Importa el DTO (Data Transfer Object) que se enviará como respuesta
import org.example.dto.DnaRequest; // Importa el DTO que se recibirá en la solicitud

@RestController
@RequestMapping("/mutant") // Define que las rutas de este controlador estarán bajo "/mutant"
public class DnaController {
    private final DnaService dnaService; // Servicio que analizará el ADN, inyectado a través del constructor

    // Constructor del controlador, recibe una instancia de DnaService
    public DnaController(DnaService dnaService) {
        this.dnaService = dnaService; // Asigna el servicio recibido a la variable local
    }

    @PostMapping // Indica que este método manejará solicitudes HTTP POST
    public ResponseEntity<DnaResponse> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        // El cuerpo de la petición se mapea a un objeto DnaRequest, y se valida con @Valid

        boolean isMutant = dnaService.analyzeDna(dnaRequest.getDna());
        // Llama al método analyzeDna del servicio para determinar si el ADN es de un mutante

        DnaResponse dnaResponse = new DnaResponse(isMutant);
        // Crea un objeto de respuesta que contiene el resultado (si es mutante o no)

        if (isMutant) {
            return ResponseEntity.ok(dnaResponse);
            // Si el ADN es de un mutante, devuelve una respuesta con estado HTTP 200 (OK) y el objeto dnaResponse
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dnaResponse);
            // Si no es mutante, devuelve una respuesta con estado HTTP 403 (FORBIDDEN) y el objeto dnaResponse
        }
    }
}*/