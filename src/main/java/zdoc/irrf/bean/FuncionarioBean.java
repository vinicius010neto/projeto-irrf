
package zdoc.irrf.bean;
import zdoc.irrf.model.Funcionario;
import zdoc.irrf.dao.FuncionarioDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "funcionarioBean")
@ViewScoped
public class FuncionarioBean implements Serializable{
private Funcionario funcionario = new Funcionario();
private List<Funcionario> funcionarios;
private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();


public void listar() {
	funcionarios = funcionarioDAO.listar();
}
public void inserir() {
	funcionarioDAO.inserir(funcionario);
	funcionario = new Funcionario();
	listar();
}

public void atualizar() {
	funcionarioDAO.atualizar(funcionario);
	funcionario = new Funcionario();
	listar();
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


}
