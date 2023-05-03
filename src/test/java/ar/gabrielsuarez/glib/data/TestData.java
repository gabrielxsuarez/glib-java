package ar.gabrielsuarez.glib.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestData  {
	
	@Test
	protected void test() {
		Data data = new Data();
		data.set("estado", 0);
		data.set("persona.nombre", "Gabriel");
		data.set("persona.apellido", "Suarez");
		data.set("persona.lenguajes.0", "Java");
		assertEquals(0, data.integer("estado"));
		assertEquals("0", data.string("estado"));
		assertEquals("Gabriel", data.string("persona.nombre"));
		assertEquals("Suarez", data.string("persona.apellido"));
		assertEquals("Suarez", data.map("persona").get("apellido"));
		assertEquals("Suarez", data.data("persona").get("apellido"));
		assertEquals("Java", data.string("persona.lenguajes.0"));
		
		data = new Data();
		data.set("1", "Gabriel");
		assertEquals("Gabriel", data.toList().get(1));
		
		data = new Data();
		data.set("-1", "Gabriel");
		assertEquals("Gabriel", data.get("-1"));
	}
}
