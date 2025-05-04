package zdoc.irrf.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import zdoc.irrf.model.Funcionario;

public class FuncionarioDAO {

	private final String URL = "jdbc:postgresql://localhost:5446/db_name";
	private final String USER = "db_user";
	private final String PASS = "db_pass";

	private Connection getConnection() throws SQLException {
		 try {
		        Class.forName("org.postgresql.Driver"); 
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		return DriverManager.getConnection(URL, USER, PASS);
	}

	public List<Funcionario> listar() {
		List<Funcionario> funcionarios = new ArrayList<>();
		String sql = "SELECT * FROM funcionarios";

		try (Connection conn = getConnection();
		     Statement stmt = conn.createStatement();
		     ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("id"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setSalario(rs.getBigDecimal("salario"));
				funcionario.setProventos(rs.getBigDecimal("proventos"));
				funcionario.setDescontos(rs.getBigDecimal("descontos"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return funcionarios;
	}

	public void inserir(Funcionario funcionario) {
		String sql = "INSERT INTO funcionarios (nome, cpf, salario, proventos, descontos) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, funcionario.getNome());
			pstmt.setString(2, funcionario.getCpf());
			pstmt.setBigDecimal(3, funcionario.getSalario());
			pstmt.setBigDecimal(4, funcionario.getProventos());
			pstmt.setBigDecimal(5, funcionario.getDescontos());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizar(Funcionario funcionario) {
		String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, salario = ?, proventos = ?, descontos = ? WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, funcionario.getNome());
			pstmt.setString(2, funcionario.getCpf());
			pstmt.setBigDecimal(3, funcionario.getSalario());
			pstmt.setBigDecimal(4, funcionario.getProventos());
			pstmt.setBigDecimal(5, funcionario.getDescontos());
			pstmt.setInt(6, funcionario.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletar(int id) {
		String sql = "DELETE FROM funcionarios WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public double calcularIRRF(double salario, double proventos) {
		double irrf = 0;
		String sql = "SELECT calcula_irrf(CAST(? AS numeric), CAST(? AS numeric))";


		try (Connection conn = getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDouble(1, salario);
			pstmt.setDouble(2, proventos);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					irrf = rs.getDouble(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return irrf;
	}
}
