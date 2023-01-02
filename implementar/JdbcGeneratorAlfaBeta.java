//package io.github.gabrielxsuarez.zenx.sql.generator;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import ar.gabrielsuarez.glib.G;
//import ar.gabrielsuarez.glib.sql.generator.JdbcGenerator;
//import ar.gabrielsuarez.glib.sql.metadata.Catalog;
//
//class JdbcGeneratorAlfaBeta {
//
//	public static void main(String[] args) {
//		List<File> files = fdbs("C:/Alfabeta-backup/bases/");
//		for (File file : files) {
//			DataSource dataSource = dataSource(file);
//			String catalogName = file.getName().substring(0, file.getName().lastIndexOf('.'));
//			Catalog catalog = Catalog.load(dataSource, catalogName);
//			JdbcGenerator generator = new JdbcGenerator(catalog);
//			generator.catalogPackage = "net.alfabeta.sql." + catalogName.toLowerCase();
//			generator.tablePackage = "net.alfabeta.sql." + catalogName.toLowerCase();
//			generator.generate(G.desktopPath("alfabeta"), true);
//		}
//	}
//
//	public static List<File> fdbs(String path) {
//		List<File> list = new ArrayList<>();
//		File folder = new File(path);
//		if (folder.exists()) {
//			File[] files = folder.listFiles();
//			if (files != null) {
//				for (int i = 0; i < files.length; i++) {
//					File file = files[i];
//					if (file.isFile() && file.getName().endsWith(".fdb")) {
//						list.add(file);
//					}
//				}
//			}
//		}
//		return list;
//	}
//
//	public static DataSource dataSource(File file) {
//		HikariConfig config = new HikariConfig();
//		config.setJdbcUrl("jdbc:firebirdsql://localhost:3050/" + file.getAbsolutePath());
//		config.setUsername(System.getenv("user"));
//		config.setPassword(System.getenv("pass"));
//		config.setDriverClassName("org.firebirdsql.jdbc.FBDriver");
//		config.addDataSourceProperty("charSet", "ISO8859_1");
//		config.addDataSourceProperty("encoding", "ISO8859_1");
//		HikariDataSource hikariDataSource = new HikariDataSource(config);
//		return hikariDataSource;
//	}
//}
