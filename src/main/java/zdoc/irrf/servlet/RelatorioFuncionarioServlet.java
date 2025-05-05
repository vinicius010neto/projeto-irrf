package zdoc.irrf.servlet;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import zdoc.irrf.dao.FuncionarioDAO;
import zdoc.irrf.model.Funcionario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@WebServlet("/relatorio-funcionario")
public class RelatorioFuncionarioServlet extends HttpServlet {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int funcionarioId = Integer.parseInt(req.getParameter("id"));

        Funcionario funcionario = funcionarioDAO.buscarPorId(funcionarioId); 
        List<Funcionario> lista = Collections.singletonList(funcionario);

        try {
        	
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id", funcionario.getId());
            parameters.put("nome", funcionario.getNome());
            parameters.put("cpf", funcionario.getCpf());
            parameters.put("salario", funcionario.getSalario());
            parameters.put("proventos", funcionario.getProventos());
            parameters.put("irrf", funcionario.getIrrfCalculado());

       
            InputStream jasperStream = getClass().getClassLoader()
            	    .getResourceAsStream("zdoc/irrf/report/funcionario.jasper");

            	JasperPrint print = JasperFillManager.fillReport(jasperStream, parameters, dataSource);

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=relatorio_funcionario_" + funcionario.getId() + ".pdf");
            JasperExportManager.exportReportToPdfStream(print, resp.getOutputStream());

        } catch (JRException e) {
            throw new ServletException(e);
        }
    }
}
