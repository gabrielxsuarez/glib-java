//package io.github.gabrielxsuarez.zenx.sql.generator;
//
//import java.util.List;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import io.github.gabrielxsuarez.zenx.sql.SqlMetadata;
//import io.github.gabrielxsuarez.zenx.thread.Future;
//
//class JdbcGeneratorHipotecario {
//
//	public static void main(String[] args) {
//		HikariConfig config = new HikariConfig();
//		config.setJdbcUrl("jdbc:sqlserver://homomssql16:1985");
//		config.setUsername(System.getenv("user"));
//		config.setPassword(System.getenv("pass"));
//		config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		HikariDataSource hikariDataSource = new HikariDataSource(config);
//
//		List<String> catalogs = SqlMetadata.catalogs(hikariDataSource);
//		for (String catalog : catalogs) {
//			Future.call(() -> generate(hikariDataSource, catalog));
//		}
//	}
//
//	public static void generate(HikariDataSource hikariDataSource, String catalog) {
//		System.out.println(catalog);
//		SqlMetadata metadata = SqlMetadata.load(hikariDataSource, catalog);
//		String basePackage = "ar.com.hipotecario.sql";
//		String dataSource = "SqlServer.dataSource(\"" + catalog + "\")";
//		JdbcGenerator jdbcGenerator = JdbcGenerator.load(metadata, basePackage, dataSource, catalog);
//		jdbcGenerator.generate();
//	}
//}
