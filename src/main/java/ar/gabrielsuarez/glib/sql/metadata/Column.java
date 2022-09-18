package ar.gabrielsuarez.glib.sql.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Base;
import ar.gabrielsuarez.glib.sql.SqlResponse;

public class Column extends Base {

	/* ========== ATTRIBUTES ========== */
	public String TABLE_CAT; // table catalog (may be null)
	public String TABLE_SCHEM; // table schema (may be null)
	public String TABLE_NAME; // table name
	public String COLUMN_NAME; // column name
	public Integer DATA_TYPE; // SQL type from java.sql.Types
	public String TYPE_NAME; // Data source dependent type name, for a UDT the type name is fully qualified
	public Integer COLUMN_SIZE; // column size.
	public Integer BUFFER_LENGTH; // is not used.
	public Integer DECIMAL_DIGITS; // the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
	public Integer NUM_PREC_RADIX; // Radix (typically either 10 or 2)
	public Integer NULLABLE; // is NULL allowed.
	public String REMARKS; // comment describing column (may be null)
	public String COLUMN_DEF; // default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
	public Integer SQL_DATA_TYPE; // unused
	public Integer SQL_DATETIME_SUB; // unused
	public Integer CHAR_OCTET_LENGTH; // for char types the maximum number of bytes in the column
	public Integer ORDINAL_POSITION; // index of column in table (starting at 1)
	public String IS_NULLABLE; // ISO rules are used to determine the nullability for a column. (YES, NO, empty)
	public String SCOPE_CATALOG; // catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
	public String SCOPE_SCHEMA; // schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
	public String SCOPE_TABLE; // table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
	public Short SOURCE_DATA_TYPE; // source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
	public String IS_AUTOINCREMENT; // Indicates whether this column is auto incremented. (YES, NO, empty)
	public String IS_GENERATEDCOLUMN; // Indicates whether this is a generated column. (YES, NO, empty)

	/* ========== INSTANCE ========== */
	public static List<Column> load(DataSource dataSource, Table table) {
		List<Column> columns = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metadata = connection.getMetaData();
			columns = SqlResponse.toList(Column.class, metadata.getColumns(table.TABLE_CAT, table.TABLE_SCHEM, table.TABLE_NAME, null));
			G.trimAllFields(columns);
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return columns;
	}

	/* ========== METHODS ========== */
	public Boolean isAutoIncrement() {
		return "YES".equalsIgnoreCase(IS_AUTOINCREMENT);
	}
}