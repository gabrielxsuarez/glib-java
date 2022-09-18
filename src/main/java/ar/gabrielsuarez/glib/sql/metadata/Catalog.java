package ar.gabrielsuarez.glib.sql.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.gabrielsuarez.glib.G;
import ar.gabrielsuarez.glib.data.Base;

public class Catalog extends Base {

	/* ========== ATTRIBUTES ========== */
	public String TABLE_CAT; // catalog name

	/* ========== EXTRAS ========== */
	public String name; // catalog name
	public List<Table> tables = new ArrayList<>();

	/* ========== INSTANCE ========== */
	public static Catalog load(DataSource dataSource, String catalogName) {
		Catalog catalog = new Catalog();
		catalog.TABLE_CAT = catalogName;
		catalog.tables = Table.load(dataSource, catalog);
		G.trimAllFields(catalog);
		return catalog;
	}
}