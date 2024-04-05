package br.unitins.irs.crud.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@QuarkusTest
class PdfServiceTest {

    @Inject
    PdfService service;

    @Test
    @TestTransaction
    @DisplayName("Deve gerar o PDF corretamente")
    void test() throws IOException {
        byte[] testes = service.generatePdf("teste");
        System.out.println(testes);
    }
}