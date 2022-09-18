package ar.gabrielsuarez.glib.generator;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.sql.metadata.Catalog;
import ar.gabrielsuarez.glib.sql.metadata.Column;
import ar.gabrielsuarez.glib.sql.metadata.Table;

public class JdbcGenerator {

	/* ========== ATTRIBUTES ========== */
	private Catalog catalog;

	/* ========== OPTIONS ========== */
	public String catalogPackage;
	public String tablePackage;
	public String catalogName;
	public String prefixCatalogClassName = "Catalog";
	public String prefixTableClassName = "Table";
	public String prefixTableObjectName = "";
	public String prefixRowClassName = "Row";
	public String prefixRowObjectName = "row";
	public String tagTables = "TABLES";
	public String tagInstance = "INSTANCE";
	public String tagAttributes = "ATTRIBUTES";
	public String tagRow = "ROW";
	public Boolean replaceFiles = true;
	public Boolean printSource = true;
	public String basePath;

	/* ========== INSTANCE ========== */
	public JdbcGenerator(Catalog catalog) {
		this.catalog = catalog;
	}

	/* ========== CLASSES ========== */
	public static class JdbcCatalog {
		public String catalogPackage;
		public Set<String> catalogImports = new TreeSet<>();
		public String catalogClassName;
		public List<JdbcTable> tables = new ArrayList<>();
	}

	public static class JdbcTable {
		public String tablesPackage;
		public Set<String> tableImports = new TreeSet<>();
		public String tableClassName;
		public String tableObjectName;
		public String rowClassName;
		public String rowObjectName;
		public String tableName;
		public Boolean isView;
		public List<JdbcColumn> columns = new ArrayList<>();
		public List<JdbcColumn> columnsKeys = new ArrayList<>();
		public List<JdbcColumn> columnsNoKeys = new ArrayList<>();
		public List<JdbcColumn> columnsNoAutoIncrement = new ArrayList<>();
	}

	public static class JdbcColumn {
		public String javaClass;
		public String javaName;
		public String columnName;
	}

	/* ========== DATA ========== */
	protected JdbcCatalog jdbcCatalog() {
		JdbcCatalog jdbcCatalog = new JdbcCatalog();
		jdbcCatalog.catalogPackage = catalogPackage;
		jdbcCatalog.catalogClassName = prefixCatalogClassName + G.pascalCase(catalog.TABLE_CAT);
		jdbcCatalog.catalogImports.add("javax.sql.DataSource");
		Set<String> tables = new HashSet<>();
		for (Table table : catalog.tables) {
			if (!table.isSystemTable() && !tables.contains(table.TABLE_NAME)) {
				JdbcTable jdbcTable = jdbcTable(table);
				jdbcCatalog.tables.add(jdbcTable);
				tables.add(table.TABLE_NAME);
				if (catalogPackage != null && !catalogPackage.equals(tablePackage)) {
					jdbcCatalog.catalogImports.add(jdbcTable.tablesPackage + "." + jdbcTable.tableClassName);
				}
			}
		}
		return jdbcCatalog;
	}

	protected JdbcTable jdbcTable(Table table) {
		JdbcTable jdbcTable = new JdbcTable();
		jdbcTable.tablesPackage = tablePackage;
		jdbcTable.tableImports.add("ar.gabrielsuarez.glib.data.Base");
		jdbcTable.tableImports.add("ar.gabrielsuarez.glib.sql.SqlRequest");
		jdbcTable.tableImports.add("ar.gabrielsuarez.glib.sql.SqlResponse");
		jdbcTable.tableImports.add("java.util.List");
		jdbcTable.tableImports.add("javax.sql.DataSource");
		jdbcTable.tableClassName = prefixTableClassName + G.pascalCase(table.TABLE_NAME);
		jdbcTable.tableObjectName = prefixTableObjectName + G.camelCase(table.TABLE_NAME);
		jdbcTable.rowClassName = prefixRowClassName + G.pascalCase(table.TABLE_NAME);
		jdbcTable.rowObjectName = prefixRowObjectName + G.pascalCase(table.TABLE_NAME);
		jdbcTable.tableName = table.TABLE_NAME;
		jdbcTable.isView = table.isView();
		for (Column column : table.columns) {
			JdbcColumn jdbcColumn = jdbcColumn(column);
			G.add(jdbcTable.columns, jdbcColumn);
			G.add(jdbcTable.columnsKeys, jdbcColumn, table.isPrimaryKey(column));
			G.add(jdbcTable.columnsNoKeys, jdbcColumn, !table.isPrimaryKey(column));
			G.add(jdbcTable.columnsNoAutoIncrement, jdbcColumn, !column.isAutoIncrement());
		}
		return jdbcTable;
	}

	protected JdbcColumn jdbcColumn(Column column) {
		JdbcColumn jdbcColumn = new JdbcColumn();
		jdbcColumn.javaClass = javaType(column);
		jdbcColumn.javaName = column.COLUMN_NAME;
		jdbcColumn.columnName = column.COLUMN_NAME;
		return jdbcColumn;
	}

	/* ========== GENERATE ========== */
	public Map<String, String> generate() {
		Map<String, String> map = new TreeMap<>();
		try {
			Handlebars handlebars = new Handlebars();
			handlebars.prettyPrint(true);
			Template jdbcCatalogTemplate = handlebars.compile("jdbc-generator/JdbcCatalog");
			Template jdbcTablesTemplate = handlebars.compile("jdbc-generator/JdbcTable");
			JdbcCatalog jdbcCatalog = jdbcCatalog();
			if (jdbcCatalog != null) {
				Map<String, Object> data = G.toMap(jdbcCatalog);
				data.putAll(G.toMap(this));
				String javaClass = jdbcCatalogTemplate.apply(data);
				String key = key(jdbcCatalog.catalogPackage, jdbcCatalog.catalogClassName);
				map.put(key, javaClass);
				if (printSource) {
					System.out.println(javaClass);
				}
				if (basePath != null) {
					G.writeFile(basePath, key.replace('.', '/') + ".java", javaClass);
				}
			}
			for (JdbcTable jdbcTable : jdbcCatalog.tables) {
				Map<String, Object> data = G.toMap(jdbcTable);
				data.putAll(G.toMap(this));
				String javaClass = jdbcTablesTemplate.apply(data);
				String key = key(jdbcTable.tablesPackage, jdbcTable.tableClassName);
				map.put(key, javaClass);
				if (printSource) {
					System.out.println(javaClass);
				}
				if (basePath != null) {
					G.writeFile(basePath, key.replace('.', '/') + ".java", javaClass);
				}
			}
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return map;
	}

	/* ========== TYPES ========== */
	protected String key(String fullPackage, String className) {
		if (fullPackage != null) {
			return fullPackage + "." + className;
		}
		return className;
	}

	protected String javaType(Column column) {
		String javaType = null;
		javaType = Types.BIT == column.DATA_TYPE ? "Boolean" : javaType;
		javaType = Types.TINYINT == column.DATA_TYPE ? "Byte" : javaType;
		javaType = Types.SMALLINT == column.DATA_TYPE ? "Short" : javaType;
		javaType = Types.INTEGER == column.DATA_TYPE ? "Integer" : javaType;
		javaType = Types.BIGINT == column.DATA_TYPE ? "java.math.BigInteger" : javaType;
		javaType = Types.FLOAT == column.DATA_TYPE ? "java.math.BigDecimal" : javaType;
		javaType = Types.REAL == column.DATA_TYPE ? "java.math.BigDecimal" : javaType;
		javaType = Types.DOUBLE == column.DATA_TYPE ? "java.math.BigDecimal" : javaType;
		javaType = Types.NUMERIC == column.DATA_TYPE ? "java.math.BigDecimal" : javaType;
		javaType = Types.DECIMAL == column.DATA_TYPE ? "java.math.BigDecimal" : javaType;
		javaType = Types.CHAR == column.DATA_TYPE ? "String" : javaType;
		javaType = Types.VARCHAR == column.DATA_TYPE ? "String" : javaType;
		javaType = Types.LONGVARCHAR == column.DATA_TYPE ? "String" : javaType;
		javaType = Types.DATE == column.DATA_TYPE ? "java.time.LocalDate" : javaType;
		javaType = Types.TIME == column.DATA_TYPE ? "java.time.LocalTime" : javaType;
		javaType = Types.TIMESTAMP == column.DATA_TYPE ? "java.time.LocalDateTime" : javaType;
		javaType = Types.BINARY == column.DATA_TYPE ? "byte[]" : javaType;
		javaType = Types.VARBINARY == column.DATA_TYPE ? "byte[]" : javaType;
		javaType = Types.LONGVARBINARY == column.DATA_TYPE ? "byte[]" : javaType;
		javaType = Types.NULL == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.OTHER == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.JAVA_OBJECT == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.DISTINCT == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.STRUCT == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.ARRAY == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.BLOB == column.DATA_TYPE ? "byte[]" : javaType;
		javaType = Types.CLOB == column.DATA_TYPE ? "byte[]" : javaType;
		javaType = Types.REF == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.DATALINK == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.BOOLEAN == column.DATA_TYPE ? "Boolean" : javaType;
		javaType = Types.ROWID == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.NCHAR == column.DATA_TYPE ? "String" : javaType;
		javaType = Types.NVARCHAR == column.DATA_TYPE ? "String" : javaType;
		javaType = Types.LONGNVARCHAR == column.DATA_TYPE ? "String" : javaType;
		javaType = Types.NCLOB == column.DATA_TYPE ? "byte[]" : javaType;
		javaType = Types.SQLXML == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.REF_CURSOR == column.DATA_TYPE ? "Object" : javaType;
		javaType = Types.TIME_WITH_TIMEZONE == column.DATA_TYPE ? "java.time.ZonedDateTime" : javaType;
		javaType = Types.TIMESTAMP_WITH_TIMEZONE == column.DATA_TYPE ? "java.time.ZonedDateTime" : javaType;
		javaType = "VARCHAR".equalsIgnoreCase(column.TYPE_NAME) ? "String" : javaType;
		if (javaType == null) {
			javaType = "Object";
		}
		return javaType;
	}
}
