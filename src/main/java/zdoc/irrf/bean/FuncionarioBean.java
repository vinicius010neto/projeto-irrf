
package zdoc.irrf.bean;

import zdoc.irrf.model.Funcionario;
import zdoc.irrf.report.GeradorRelatorio;
import zdoc.irrf.dao.FuncionarioDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name = "funcionarioBean")
@ViewScoped
public class FuncionarioBean implements Serializable {
	private Funcionario funcionario = new Funcionario();
	private List<Funcionario> funcionarios;
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	public void listar() {
		funcionarios = funcionarioDAO.listar();
		for (Funcionario f : funcionarios) {

			double irrf = funcionarioDAO.calcularIRRF(f.getSalario().doubleValue(), f.getProventos().doubleValue());

			f.setIrrfCalculado(irrf);
		}
	}

	public void salvar() {
		if (funcionario.getId() == 0) {
			funcionarioDAO.inserir(funcionario);
		} else {
			funcionarioDAO.atualizar(funcionario);
		}
		funcionario = new Funcionario();
		listar();
	}

	public void editar(Funcionario f) {
		this.funcionario = f;
	}

	public void deletar(int id) {
		funcionarioDAO.deletar(id);
		funcionario = new Funcionario();
		listar();
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		if (funcionarios == null) {
			listar();
		}
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public double getIrrfCalculado(Funcionario funcionario) {
		return funcionarioDAO.calcularIRRF(funcionario.getSalario().doubleValue(),
				funcionario.getProventos().doubleValue());
	}

	public void gerarRelatorioFuncionario(Funcionario funcionario) {
		GeradorRelatorio gerador = new GeradorRelatorio();
		gerador.gerarPDF(Arrays.asList(funcionario));
	}

}
