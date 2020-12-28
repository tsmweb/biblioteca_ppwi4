package br.com.tsmweb.biblioteca.models.service.reports;

import java.io.InputStream;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tsmweb.biblioteca.models.config.ConfigProjeto;
import br.com.tsmweb.biblioteca.models.service.components.PrintJasperReport;
import br.com.tsmweb.biblioteca.models.service.exception.NegocioException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportsService {

	@Autowired
	private DataSource dataSource;
	
	public JasperPrint downloadReportPdf(PrintJasperReport printJasperReport) {
		JasperReport fileOut = openCompileReports(printJasperReport.getFile());
		
		try {
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(fileOut, printJasperReport.getParams(), dataSource.getConnection());
			
			return jasperPrint;
		} catch(SQLException e) {
			throw new NegocioException("Erro na conexão com a base de dados ", e.getCause());
		} catch (JRException e) {
			throw new NegocioException("Erro na geração do relatório em PDF ", e.getCause());
		}
	}
	
	public byte[] generateNativeSqlReport(PrintJasperReport printJasperReport) {
		JasperReport fileOut = openCompileReports(printJasperReport.getFile());
		
		try {
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(fileOut, printJasperReport.getParams(), dataSource.getConnection());
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch(SQLException e) {
			throw new NegocioException("Erro na conexão com a base de dados ", e.getCause());
		} catch (JRException e) {
			throw new NegocioException("Erro na geração do relatório em PDF ", e.getCause());
		}
	}
 	
	public byte[] generateListReport(PrintJasperReport printJasperReport) {
		JasperReport fileOut = openCompileReports(printJasperReport.getFile());
		
		try {
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(fileOut, 
							printJasperReport.getParams(), 
							new JRBeanCollectionDataSource(printJasperReport.getCollection()));
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			throw new NegocioException("Erro na geração do relatório em PDF ", e.getCause());
		}
	}
	
	private JasperReport openCompileReports(String file) {
		InputStream fileIn = this.getClass().getResourceAsStream(ConfigProjeto.DIR_REPORTS + file + ".jrxml");
		
		try {
			JasperReport fileOut = JasperCompileManager.compileReport(fileIn);	
			return fileOut;
		} catch(JRException e) {
			throw new NegocioException("Erro na geração do relatório em PDF ", e.getCause());
		}
	}
	
}
