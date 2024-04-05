package br.unitins.irs.crud.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;
import java.net.URL;

@ApplicationScoped
public class PdfService {

    public byte[] generatePdf(String text) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream imageStream = classLoader.getResourceAsStream("report.html");
        String pdfDest = getReportsPath() + "/pdf.pdf";

        // Lendo o conteúdo do arquivo HTML
        StringBuilder htmlString = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(imageStream); BufferedReader htmlReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = htmlReader.readLine()) != null) {
                htmlString.append(line);
            }
        }

        // Criando um PDF
        PdfWriter writer = new PdfWriter(pdfDest);
        PdfDocument pdf = new PdfDocument(writer);
        ConverterProperties properties = new ConverterProperties();
        HtmlConverter.convertToPdf(htmlString.toString(), pdf, properties);
        pdf.close();

        System.out.println("Conversão concluída. PDF gerado em: " + pdfDest);
        return loadPDFBytes("/reports/report.pdf");
    }

    public byte[] loadPDFBytes(String pdfFilePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(pdfFilePath);

            if (inputStream != null) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();

                return buffer.toByteArray();
            } else {
                System.err.println("Arquivo PDF não encontrado: " + pdfFilePath);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo PDF: " + e.getMessage());
            return null;
        }
    }

    public String getReportsPath() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resourceUrl = classLoader.getResource("reports");

            if (resourceUrl != null) {
                return resourceUrl.getPath();
            } else {
                System.err.println("Diretório /reports não encontrado.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter o caminho até /reports: " + e.getMessage());
            return null;
        }
    }
}
