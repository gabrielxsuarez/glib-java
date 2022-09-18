package ar.gabrielsuarez.glib.data;

import ar.gabrielsuarez.glib.G;

public abstract class Base {

	/* ========== TOSTRING ========== */
	public String toString() {
		return G.toJson(this);
	}
}
