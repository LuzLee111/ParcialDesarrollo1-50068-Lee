package TestParcial;

import org.example.services.DnaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DnaServiceTest {

    private final DnaService dnaService = new DnaService(null); // Pasamos null porque no usaremos el repositorio en los tests

    @Test
    public void testRowAndColum() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(dnaService.analyzeDna(dna));
    }

    @Test
    public void testRows() {
        String[] dna = {
                "AAAATG",
                "TGCAGT",
                "GCTTCC",
                "CCCCTG",
                "GTAGTC",
                "AGTCAC"
        };
        assertTrue(dnaService.analyzeDna(dna));
    }

    @Test
    public void testColumns() {
        String[] dna = {
                "AGAATG",
                "TGCAGT",
                "GCTTCC",
                "GTCCTC",
                "GTAGTC",
                "GGTCAC"
        };
        assertTrue(dnaService.analyzeDna(dna));
    }

    @Test
    public void testError() {
        String[] dna = {
                "AGTCAA",
                "GTCACG",
                "CTGGTA",
                "ACTGCC",
                "CCTAGT",
                "AAGCAA"
        };
        assertFalse(dnaService.analyzeDna(dna));
    }

    @Test
    public void testMainDiagonals() {
        String[] dna = {
                "AGAATG",
                "TACAGT",
                "GCAGCC",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(dnaService.analyzeDna(dna));
    }

    @Test
    public void testError1() {
        String[] dna = {
                "AGTCAG",
                "AAGTCC",
                "CCTGGG",
                "ATTCAA",
                "CTTAGA",
                "GTACCC"
        };
        assertFalse(dnaService.analyzeDna(dna));
    }

    @Test
    public void testSecondaryLeftDiagonals() {
        String[] dna = {
                "ATAATG",
                "GTTAGT",
                "GGCTCG",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(dnaService.analyzeDna(dna));
    }

    @Test
    public void testSecondaryRightDiagonals() {
        String[] dna = {
                "ATAATG",
                "GTATGA",
                "GCTTAG",
                "TTTAGG",
                "GTAGTC",
                "AGTCAA"
        };
        assertFalse(dnaService.analyzeDna(dna));
    }

    @Test
    public void testNonMutant() {
        String[] dna = {
                "ATGATG",
                "GTCTTA",
                "AATTGG",
                "ACTAGT",
                "GGAGTC",
                "AGGCAA"
        };
        assertFalse(dnaService.analyzeDna(dna));
    }

    @Test
    public void testRows2() {
        String[] dna = {
                "TTTTTA",
                "AAAATA",
                "GGGGTA",
                "CCCCTA",
                "TATATA",
                "ACGTAC"
        };
        assertFalse(dnaService.analyzeDna(dna));
    }


    @Test
    public void testMutant1() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(dnaService.analyzeDna(dna));
    }

    @Test
    public void testNonMutant1() {
        String[] dna = {
                "AAAT",
                "AACC",
                "AAAC",
                "CGGG"
        };
        assertFalse(dnaService.analyzeDna(dna));
    }
}