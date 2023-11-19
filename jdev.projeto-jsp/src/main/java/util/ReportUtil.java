package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte[] geraRelatorioPDF(List listaDado, String nomeRelatorio, ServletContext servletContext)
			throws Exception {

		// Cria a lista de dados que vem do SQL
		// Lista de dados já irá vir filtrada do sql
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDado);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		// Carregar o jasper passando os dados
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);

	}

	public byte[] geraRelatorioPDF(List listaDado, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {

		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDado);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		//JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);

	}

	public byte[] geraRelatorioExcel(List listaDado, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletContext) throws Exception {

		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDado);

		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		System.out.println("Parar");
		
	//	JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);


		
		JRExporter exporter = new JRXlsExporter(); //Excel
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		exporter.exportReport();
		
		return baos.toByteArray();

	}


}
