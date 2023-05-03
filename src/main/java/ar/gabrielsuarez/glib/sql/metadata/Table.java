package ar.gabrielsuarez.glib.sql.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Base;
import ar.gabrielsuarez.glib.sql.SqlResponse;

public class Table extends Base {

	/* ========== ATTRIBUTES ========== */
	public String TABLE_CAT; // table catalog (may be null)
	public String TABLE_SCHEM; // table schema (may be null)
	public String TABLE_NAME; // table name
	public String TABLE_TYPE; // table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
	public String REMARKS; // explanatory comment on the table
	public String TYPE_CAT; // the types catalog (may be null)
	public String TYPE_SCHEM; // the types schema (may be null)
	public String TYPE_NAME; // type name (may be null)
	public String SELF_REFERENCING_COL_NAME; // name of the designated "identifier" column of a typed table (may be null)
	public String REF_GENERATION; // specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null)

	/* ========== EXTRAS ========== */
	public List<PrimaryKey> primaryKeys = new ArrayList<>();
	public List<Column> columns = new ArrayList<>();

	/* ========== INSTANCE ========== */
	public static List<Table> load(DataSource dataSource, Catalog catalog) {
		List<Table> tables = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metadata = connection.getMetaData();
			tables = SqlResponse.toList(Table.class, metadata.getTables(catalog.TABLE_CAT, null, null, null));
			G.trimAllFields(tables);
			for (Table table : tables) {
				table.primaryKeys = PrimaryKey.load(dataSource, table);
				table.columns = Column.load(dataSource, table);
			}
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return tables;
	}

	/* ========== METHODS ========== */
	public Boolean isPrimaryKey(Column column) {
		for (PrimaryKey primaryKey : primaryKeys) {
			if (primaryKey.COLUMN_NAME.equals(column.COLUMN_NAME)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isSystemTable() {
		Boolean isSystemTable = false;
		isSystemTable |= "SYSTEM TABLE".equalsIgnoreCase(TABLE_TYPE);
		isSystemTable |= "INFORMATION_SCHEMA".equalsIgnoreCase(TABLE_SCHEM);
		isSystemTable |= "sys".equalsIgnoreCase(TABLE_SCHEM);
		isSystemTable |= "sysdiagrams".equalsIgnoreCase(TABLE_NAME);
		isSystemTable |= TABLE_NAME.toLowerCase().startsWith("syncobj");
		return isSystemTable;
	}

	public Boolean isView() {
		return "VIEW".equals(TABLE_TYPE);
	}
}