package zdoc.irrf.model;
import java.io.Serializable;

import java.math.BigDecimal;

public class Funcionario implements Serializable {

	private int id;
	private String nome;
	private String cpf;
	private BigDecimal salario;
	private BigDecimal proventos;
	private BigDecimal descontos;

	public Funcionario() {
		this.salario = BigDecimal.ZERO;
		this.proventos = BigDecimal.ZERO;
		this.descontos = BigDecimal.ZERO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public BigDecimal getProventos() {
		return proventos;
	}

	public void setProventos(BigDecimal proventos) {
		this.proventos = proventos;
	}

	public BigDecimal getDescontos() {
		return descontos;
	}

	public void setDescontos(BigDecimal descontos) {
		this.descontos = descontos;
	}

	public BigDecimal getSalarioLiquido() {
		return salario.add(proventos).subtract(descontos);

	}

}
