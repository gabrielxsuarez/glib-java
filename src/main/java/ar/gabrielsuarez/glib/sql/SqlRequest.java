package ar.gabrielsuarez.glib.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import ar.gabrielsuarez.glib.G;

public class SqlRequest {

	/* ========== ATTRIBUTES ========== */
	private DataSource dataSource;
	private StringBuilder sql = new StringBuilder();
	private List<Object> parameters = new ArrayList<>();

	/* ========== INSTANCE ========== */
	public SqlRequest(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/* ========== BUILD ========== */
	public void append(String sql, Object... parameters) {
		Boolean generateSpace = this.sql.length() != 0;
		generateSpace = generateSpace && this.sql.charAt(this.sql.length() - 1) != '(';
		generateSpace = generateSpace && this.sql.charAt(this.sql.length() - 1) != ' ';
		if (generateSpace) {
			this.sql.append(" ");
		}
		this.sql.append(sql);
		this.parameters.addAll(Arrays.asList(parameters));
	}

	/* ========== EXECUTE ========== */
	public SqlResponse execute() {
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
				for (Integer i = 1; i <= parameters.size(); ++i) {
					ps.setObject(i, parameters.get(i - 1));
				}
				ps.execute();
				return SqlResponse.fromResultSet(ps.getResultSet());
			}
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	/* ========== TOSTRING ========== */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		for (Object parameter : parameters) {
			sb.append("\n-> ").append(parameter);
		}
		return sb.toString();
	}
}