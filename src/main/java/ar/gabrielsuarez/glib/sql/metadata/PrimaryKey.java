package ar.gabrielsuarez.glib.sql.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Base;
import ar.gabrielsuarez.glib.sql.SqlResponse;

public class PrimaryKey extends Base {

	/* ========== ATTRIBUTES ========== */
	public String TABLE_CAT; // table catalog (may be null)
	public String TABLE_SCHEM; // table schema (may be null)
	public String TABLE_NAME; // table name
	public String COLUMN_NAME; // column name
	public Short KEY_SEQ; // sequence number within primary key (a value of 1 represents the first column of the primary key, a value of 2 would represent the second column within the primary key).
	public String PK_NAME; // primary key name (may be null)

	/* ========== INSTANCE ========== */
	public static List<PrimaryKey> load(DataSource dataSource, Table table) {
		List<PrimaryKey> primaryKeys = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metadata = connection.getMetaData();
			primaryKeys = SqlResponse.toList(PrimaryKey.class, metadata.getPrimaryKeys(table.TABLE_CAT, table.TABLE_SCHEM, table.TABLE_NAME));
			G.trimAllFields(primaryKeys);
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
		return primaryKeys;
	}
}