package Model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GeradorPDF {
    private static final Font FonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font FonteSubtitulo  = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font FonteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    private static final BaseColor HeaderBg = new BaseColor(240, 240, 240);
    
    public static void gerarRelatorioCompleto(double tempoMedio, Map<String, Integer> problemasFrequentes, Map<String, Integer> chamadosStatus, Map<String, Integer> chamadosPrioridade, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio_techsolutions.pdf");

            Document document = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter footerpag = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            addHeader(document, "TechSolutions");

            Paragraph title = new Paragraph("Relatório de Chamados",FonteTitulo );
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            addSection(document, "Tempo Médio de Resolução", 
                String.format("%.2f horas", tempoMedio));

            addMapSection(document, "Problemas Mais Frequentes", problemasFrequentes, 
                "Problema", "Ocorrências");

            addMapSection(document, "Chamados por Status", chamadosStatus, 
                "Status", "Quantidade");

            addMapSection(document, "Chamados por Prioridade", chamadosPrioridade, 
                "Prioridade", "Quantidade");

            addFooter(footerpag);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void addHeader(Document document, String nome) throws DocumentException {
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);
        header.setSpacingAfter(20);

        PdfPCell sectionHeader = new PdfPCell(new Phrase(nome, FonteTitulo));
        sectionHeader.setBorder(Rectangle.BOTTOM);
        sectionHeader.setPaddingBottom(10);
        sectionHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(sectionHeader);

        document.add(header);
    }

    private static void addSection(Document document, String titulo, String informacoes) 
            throws DocumentException {
        Paragraph sectionTitulo = new Paragraph(titulo, FonteSubtitulo);
        sectionTitulo.setSpacingBefore(15);
        sectionTitulo.setSpacingAfter(10);
        document.add(sectionTitulo);

        Paragraph infoParagrafo = new Paragraph(informacoes, FonteNormal);
        infoParagrafo.setIndentationLeft(20);
        infoParagrafo.setSpacingAfter(15);
        document.add(infoParagrafo);
    }

    private static void addMapSection(Document document, String titulo, Map<String, Integer> dados, String col1Header, String col2Header) throws DocumentException {
        Paragraph sectionTitulo = new Paragraph(titulo, FonteSubtitulo);
        sectionTitulo.setSpacingBefore(15);
        sectionTitulo.setSpacingAfter(10);
        document.add(sectionTitulo);

        PdfPTable tabela = new PdfPTable(2);
        tabela.setWidthPercentage(90);
        tabela.setSpacingAfter(15);

        PdfPCell header1 = new PdfPCell(new Phrase(col1Header, FonteSubtitulo));
        PdfPCell header2 = new PdfPCell(new Phrase(col2Header, FonteSubtitulo));
        header1.setBackgroundColor(HeaderBg);
        header2.setBackgroundColor(HeaderBg);
        header1.setPadding(5);
        header2.setPadding(5);
        tabela.addCell(header1);
        tabela.addCell(header2);

        for (Map.Entry<String, Integer> entry : dados.entrySet()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(entry.getKey(), FonteNormal));
            PdfPCell cell2 = new PdfPCell(new Phrase(entry.getValue().toString(), FonteNormal));
            cell1.setPadding(5);
            cell2.setPadding(5);
            tabela.addCell(cell1);
            tabela.addCell(cell2);
        }

        document.add(tabela);
    }

    private static void addFooter(PdfWriter footerpag) {
    	footerpag.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter footerpag, Document document) {
                PdfContentByte cb = footerpag.getDirectContent();
                Phrase footer = new Phrase(String.format("Página %d", footerpag.getPageNumber()),
                    FonteNormal);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
            }
        });
    }
}