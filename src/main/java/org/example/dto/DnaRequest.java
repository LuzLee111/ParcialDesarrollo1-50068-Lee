package org.example.dto;

import lombok.*;
import org.example.validators.ValidDna;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class DnaRequest {
    @ValidDna
    private String[] dna;
}