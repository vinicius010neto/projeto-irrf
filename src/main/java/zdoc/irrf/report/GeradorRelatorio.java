package zdoc.irrf.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import zdoc.irrf.model.Funcionario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeradorRelatorio {
	public void gerarPDF(List<Funcionario> funcionarios) {
		try {
			String jasperPath = getClass()
			.getClassLoader()
			.getResource("zdoc/irrf/report/funcionario.jasper")
			.getPath();
		    String outputPath = "C:\\Users\\vja_p\\Desktop\\relatorio_funcionarios.pdf";
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(funcionarios);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("id", funcionarios.get(0).getId());
			parameters.put("nome", funcionarios.get(0).getNome());
			parameters.put("cpf", funcionarios.get(0).getCpf());
			parameters.put("salario", funcionarios.get(0).getSalario());
			parameters.put("proventos", funcionarios.get(0).getProventos());

			parameters.put("irrf", funcionarios.get(0).getIrrfCalculado());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, dataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
			System.out.println("Relatório gerado com sucesso!");
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		
	}

}