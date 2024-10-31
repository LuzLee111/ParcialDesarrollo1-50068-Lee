package org.example.services;

import org.example.entities.Dna;
import org.example.repositories.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DnaService {
    private static final int SEQUENCE_LENGTH = 4; // Se agregó la constante que faltaba

    @Autowired
    private DnaRepository dnaRepository;

    public DnaService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public static boolean isMutant(String[] dna) {
        int n = dna.length;
        int sequenceCount = 0;

        // verificamos filas
        sequenceCount += checkRows(dna, n);
        if (sequenceCount > 1) return true;

        // verificamos columnas
        sequenceCount += checkColumns(dna, n);

        // verificamos diagonales
        sequenceCount += checkDiagonals(dna, n);
        return sequenceCount > 1;
    }

    private static int checkRows(String[] dna, int n) {
        int sequenceCount = 0;
        for (int i = 0; i < n; i++) {
            int count = 1;
            for (int j = 1; j < n; j++) {
                if (dna[i].charAt(j) == dna[i].charAt(j - 1)) {
                    count++;
                    if (count == SEQUENCE_LENGTH) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    private static int checkColumns(String[] dna, int n) {
        int sequenceCount = 0;
        for (int j = 0; j < n; j++) {
            int count = 1;
            for (int i = 1; i < n; i++) {
                if (dna[i].charAt(j) == dna[i - 1].charAt(j)) {
                    count++;
                    if (count == SEQUENCE_LENGTH) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    private static int checkDiagonals(String[] dna, int n) {
        int sequenceCount = 0;

        // Diagonales de izquierda a derecha
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = 0; j <= n - SEQUENCE_LENGTH; j++) {
                if (checkDiagonal(dna, i, j, 1, 1, n)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }

        // Diagonales de derecha a izquierda
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = SEQUENCE_LENGTH - 1; j < n; j++) {
                if (checkDiagonal(dna, i, j, 1, -1, n)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }
        return sequenceCount;
    }

    private static boolean checkDiagonal(String[] dna, int x, int y, int dx, int dy, int n) {
        char first = dna[x].charAt(y);
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (x + i * dx >= n || y + i * dy >= n || y + i * dy < 0 ||
                    dna[x + i * dx].charAt(y + i * dy) != first) {
                return false;
            }
        }
        return true;
    }

    public boolean analyzeDna(String[] dna) {
        String dnaSequence = String.join(",", dna);  // delimitador es coma

        // verificamos si el ADN ya existe en la base de datos
        Optional<Dna> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant();
        }

        boolean isMutant = isMutant(dna);
        Dna dnaEntity = Dna.builder()
                .dna(dnaSequence)
                .isMutant(isMutant)
                .build();

        dnaRepository.save(dnaEntity);
        return isMutant;
    }
}


/*package org.example.services;

// Definición del paquete donde se encuentra la clase

import org.example.entities.Dna; // Importa la entidad Dna
import org.example.repositories.DnaRepository; // Importa el repositorio que interactúa con la base de datos
import org.springframework.beans.factory.annotation.Autowired; // Para la inyección de dependencias automática
import org.springframework.stereotype.Service; // Marca esta clase como un servicio

import java.util.Optional; // Para manejar posibles valores nulos de manera segura

@Service // Indica que esta clase es un servicio de Spring
public class DnaService {
    private static final int SEQUENCE_LENGTH = 4; // Longitud de la secuencia a buscar (constante definida)

    @Autowired
    private DnaRepository dnaRepository; // Repositorio que interactúa con la base de datos

    // Constructor con inyección de dependencias
    public DnaService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository; // Asigna el repositorio recibido a la variable local
    }

    // Método estático que determina si una secuencia de ADN corresponde a un mutante
    public static boolean isMutant(String[] dna) {
        int n = dna.length; // Tamaño de la matriz de ADN (n x n)
        int sequenceCount = 0; // Contador de secuencias encontradas

        // Verificamos si hay secuencias en las filas
        sequenceCount += checkRows(dna, n);
        if (sequenceCount > 1) return true; // Si ya hay más de una secuencia, es mutante

        // Verificamos columnas
        sequenceCount += checkColumns(dna, n);

        // Verificamos diagonales
        sequenceCount += checkDiagonals(dna, n);
        return sequenceCount > 1; // Es mutante si hay más de una secuencia encontrada
    }

    // Método que verifica las secuencias en las filas
    private static int checkRows(String[] dna, int n) {
        int sequenceCount = 0;
        for (int i = 0; i < n; i++) { // Itera por cada fila
            int count = 1; // Contador de caracteres consecutivos iguales
            for (int j = 1; j < n; j++) { // Itera sobre los caracteres en la fila
                if (dna[i].charAt(j) == dna[i].charAt(j - 1)) { // Si el carácter actual es igual al anterior
                    count++;
                    if (count == SEQUENCE_LENGTH) { // Si se alcanza la longitud de la secuencia deseada
                        sequenceCount++; // Se incrementa el contador de secuencias
                        if (sequenceCount > 1) return sequenceCount; // Si ya hay más de una secuencia, no seguimos buscando
                    }
                } else {
                    count = 1; // Reinicia el contador si los caracteres no son iguales
                }
            }
        }
        return sequenceCount; // Retorna la cantidad de secuencias encontradas en las filas
    }

    // Método que verifica las secuencias en las columnas
    private static int checkColumns(String[] dna, int n) {
        int sequenceCount = 0;
        for (int j = 0; j < n; j++) { // Itera por cada columna
            int count = 1; // Contador de caracteres consecutivos iguales
            for (int i = 1; i < n; i++) { // Itera sobre los caracteres en la columna
                if (dna[i].charAt(j) == dna[i - 1].charAt(j)) { // Si el carácter actual es igual al anterior en la columna
                    count++;
                    if (count == SEQUENCE_LENGTH) { // Si se alcanza la longitud de la secuencia deseada
                        sequenceCount++; // Incrementa el contador de secuencias
                        if (sequenceCount > 1) return sequenceCount; // Si ya hay más de una secuencia, se retorna
                    }
                } else {
                    count = 1; // Reinicia el contador si los caracteres no son iguales
                }
            }
        }
        return sequenceCount; // Retorna la cantidad de secuencias encontradas en las columnas
    }

    // Método que verifica las secuencias en las diagonales
    private static int checkDiagonals(String[] dna, int n) {
        int sequenceCount = 0;

        // Diagonales de izquierda a derecha (↘)
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = 0; j <= n - SEQUENCE_LENGTH; j++) {
                if (checkDiagonal(dna, i, j, 1, 1, n)) { // Si se encuentra una secuencia diagonal
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount; // Retorna si hay más de una secuencia
                }
            }
        }

        // Diagonales de derecha a izquierda (↙)
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = SEQUENCE_LENGTH - 1; j < n; j++) {
                if (checkDiagonal(dna, i, j, 1, -1, n)) { // Verifica las diagonales inversas
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount; // Retorna si hay más de una secuencia
                }
            }
        }
        return sequenceCount; // Retorna la cantidad de secuencias encontradas en las diagonales
    }

    // Método auxiliar para verificar una diagonal específica
    private static boolean checkDiagonal(String[] dna, int x, int y, int dx, int dy, int n) {
        char first = dna[x].charAt(y); // Primer carácter de la diagonal
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            // Verifica si los índices están dentro de los límites y si los caracteres coinciden
            if (x + i * dx >= n || y + i * dy >= n || y + i * dy < 0 ||
                    dna[x + i * dx].charAt(y + i * dy) != first) {
                return false; // Retorna false si los caracteres no coinciden o si se sale de los límites
            }
        }
        return true; // Retorna true si todos los caracteres de la diagonal coinciden
    }

    // Método que analiza el ADN, verificando si es mutante, y guarda el resultado en la base de datos
    public boolean analyzeDna(String[] dna) {
        String dnaSequence = String.join(",", dna); // Convierte el array de ADN en una única cadena separada por comas

        // Busca si la secuencia de ADN ya existe en la base de datos
        Optional<Dna> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant(); // Si ya existe, devuelve si es mutante o no
        }

        // Si no existe, analiza si es mutante
        boolean isMutant = isMutant(dna);
        Dna dnaEntity = Dna.builder()
                .dna(dnaSequence) // Guarda la secuencia de ADN
                .isMutant(isMutant) // Guarda si es mutante
                .build();

        dnaRepository.save(dnaEntity); // Guarda la entidad ADN en la base de datos
        return isMutant; // Retorna si es mutante o no
    }
}*/