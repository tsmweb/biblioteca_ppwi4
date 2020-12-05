package br.com.tsmweb.biblioteca.models.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import br.com.tsmweb.biblioteca.models.model.Usuario;

@Component
public class UsuarioReportPdf {

	private static final String image = "/img/ifsp.jpg";
	
	Document documento = new Document();
	
	public ByteArrayInputStream generateReport(HttpServletRequest request, List<Usuario> lista) {
		ByteArrayInputStream relatorio = null;
		String url = "http://"+request.getServerName()+":"+request.getServerPort()+image;
		
		documento.setPageSize(PageSize.A4);
		documento.setMargins(10, 10, 36, 36);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		Integer totalRegistro = 0;
		Integer pagina = 0;
		
		try {
			PdfWriter.getInstance(documento, out);
			documento.open();
			
			while (totalRegistro < lista.size()) {
				documento.add(createTitle(url));
				Paragraph paragrafo = new Paragraph();
				LineSeparator line = new LineSeparator();
				line.setOffset(-2);
				paragrafo.add(line);
				documento.add(paragrafo);
				
				paragrafo.add("\n");
				documento.add(paragrafo);
				
				documento.add(createHeader());
				
				Integer linha = 0;
				
				while (totalRegistro < lista.size() && linha < 40) {
					Usuario usuario = lista.get(totalRegistro);
					documento.add(createContent(usuario));
					totalRegistro++;
					linha++;
				}
				
				pagina = pagina + 1;
				Paragraph pag = new Paragraph("Página: "+pagina.toString());
				pag.setAlignment(Element.ALIGN_RIGHT);
				documento.add(pag);
				documento.newPage();
			}
			
			documento.close();
			relatorio = new ByteArrayInputStream(out.toByteArray());
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return relatorio;
	}
	
	public PdfPTable createTitle(String url) {
		float[] columnsWidths = {3, 5};
		
		PdfPTable table = new PdfPTable(columnsWidths);
		table.setWidthPercentage(100);
		table.getDefaultCell().setUseAscender(true);
		table.getDefaultCell().setUseDescender(true);
		
		PdfPCell column;
		Image logo = null;
		
		try {
			logo = Image.getInstance(new URL(url));
			logo.setAlignment(Image.MIDDLE);
			logo.scaleToFit(120f, 150f);
			logo.setAbsolutePosition(70, 770);
			logo.scaleAbsolute(100, 40);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		column = new PdfPCell(logo);
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
		table.addCell(column);
		
		column = new PdfPCell(new Paragraph("Relatório de Administração de Usuários"));
		column.setBorder(PdfPCell.NO_BORDER);
		column.setHorizontalAlignment(PdfPCell.ALIGN_MIDDLE);
		table.addCell(column);
		
		return table;
	}
	
	public PdfPTable createHeader() {
		PdfPTable table = new PdfPTable(3);
		
		float[] columnsWidths = new float[] {15f, 50f, 50f};
		
		try {
			table.setWidths(columnsWidths);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		PdfPCell header;
		
		header = new PdfPCell(new Phrase("Código"));
		header.setVerticalAlignment(Element.ALIGN_CENTER);
		header.setBorder(PdfPCell.NO_BORDER);
		table.addCell(header);
		
		header = new PdfPCell(new Phrase("Nome"));
		header.setVerticalAlignment(Element.ALIGN_CENTER);
		header.setBorder(PdfPCell.NO_BORDER);
		table.addCell(header);
		
		header = new PdfPCell(new Phrase("E-mail"));
		header.setVerticalAlignment(Element.ALIGN_CENTER);
		header.setBorder(PdfPCell.NO_BORDER);
		table.addCell(header);
		
		Paragraph paragrafo = new Paragraph();
		LineSeparator line = new LineSeparator();
		line.setOffset(-2);
		paragrafo.add(line);
		
		try {
			documento.add(paragrafo);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return table;
	}
	
	public PdfPTable createContent(Usuario usuario) {
		PdfPTable table = new PdfPTable(3);
		
		float[] columnsWidths = new float[] {15f, 50f, 50f};
		
		try {
			table.setWidths(columnsWidths);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		PdfPCell column;
		
		column = new PdfPCell(new Phrase(usuario.getId().toString()));
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		column.setBorder(PdfPCell.NO_BORDER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase(usuario.getUsername()));
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		column.setBorder(PdfPCell.NO_BORDER);
		table.addCell(column);
		
		column = new PdfPCell(new Phrase(usuario.getEmail()));
		column.setVerticalAlignment(Element.ALIGN_CENTER);
		column.setBorder(PdfPCell.NO_BORDER);
		table.addCell(column);
		
		return table;
	}
	
	public void createFooter() {
		
	}
	
}
