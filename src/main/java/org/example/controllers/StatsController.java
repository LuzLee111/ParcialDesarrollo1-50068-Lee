package org.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.services.StatsService;
import org.example.dto.StatsResponse;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public StatsResponse getStats() {
        return statsService.getStats();
    }
}

/*package org.example.controllers;

// Definición del paquete donde se encuentra esta clase

import org.springframework.web.bind.annotation.GetMapping; // Importa la anotación @GetMapping para manejar solicitudes GET
import org.springframework.web.bind.annotation.RequestMapping; // Define rutas para el controlador
import org.springframework.web.bind.annotation.RestController; // Marca la clase como un controlador REST
import org.example.services.StatsService; // Importa el servicio que provee las estadísticas
import org.example.dto.StatsResponse; // Importa el objeto de respuesta que será devuelto

@RestController
@RequestMapping("/stats") // Define que este controlador responderá a solicitudes en la ruta "/stats"
public class StatsController {
    private final StatsService statsService; // Servicio que se utilizará para obtener las estadísticas

    // Constructor del controlador, recibe una instancia de StatsService
    public StatsController(StatsService statsService) {
        this.statsService = statsService; // Asigna el servicio recibido a la variable local
    }

    @GetMapping // Indica que este método manejará solicitudes HTTP GET
    public StatsResponse getStats() {
        // Llama al método getStats() del servicio y retorna el objeto StatsResponse
        return statsService.getStats();
    }
}*/