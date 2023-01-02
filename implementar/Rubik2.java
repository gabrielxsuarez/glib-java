package ar.gabrielsuarez.rubik;

import java.util.Arrays;

import ar.gabrielsuarez.glib.core.XRandom;

public class Rubik2 {

	/* ========== ATRIBUTOS ========== */
	private int[][][] estado = new int[6][2][2];
	private String[] operaciones = new String[] { "R", "R2", "R'", "U", "U2", "U'", "F", "F2", "F'" };
	private int[] cache = new int[8];

	/* ========== MAIN ========== */
	public static void main(String[] args) {
		String scramble = "F2 R' U' R F U' R2 F' R";
		System.out.println(scramble);
		System.out.println();

		String[] operaciones = new String[0];
		for (int i = 0; i < 100; ++i) {
			operaciones = siguiente(operaciones);
			System.out.println(Arrays.asList(operaciones));
		}

		Rubik2 rubik2 = new Rubik2();
		rubik2.operar(scramble);
		System.out.println(rubik2);
	}

	/* ========== CONSTRUCTORES ========== */
	public Rubik2() {
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 2; ++j) {
				for (int k = 0; k < 2; ++k) {
					estado[i][j][k] = i;
				}
			}
		}
	}

	/* ========== ROTAR ========== */
	public static String[] siguiente(String[] operaciones) {
		if (operaciones.length == 0) {
			return new String[] { "R" };
		}
		String ultimaOperacion = operaciones[operaciones.length - 1];
		String siguienteOperacion = "";
		siguienteOperacion = "R".equals(ultimaOperacion) ? "R2" : siguienteOperacion;
		siguienteOperacion = "R2".equals(ultimaOperacion) ? "R'" : siguienteOperacion;
		siguienteOperacion = "R'".equals(ultimaOperacion) ? "U" : siguienteOperacion;
		siguienteOperacion = "U".equals(ultimaOperacion) ? "U2" : siguienteOperacion;
		siguienteOperacion = "U2".equals(ultimaOperacion) ? "U'" : siguienteOperacion;
		siguienteOperacion = "U'".equals(ultimaOperacion) ? "F" : siguienteOperacion;
		siguienteOperacion = "F".equals(ultimaOperacion) ? "F2" : siguienteOperacion;
		siguienteOperacion = "F2".equals(ultimaOperacion) ? "F'" : siguienteOperacion;
		if (!"F'".equals(ultimaOperacion)) {
			operaciones[operaciones.length - 1] = siguienteOperacion;
		} else {
			String[] nuevasOperaciones = new String[operaciones.length + 1];
			nuevasOperaciones[operaciones.length] = "R";
			for (int i = 0; i < nuevasOperaciones.length; ++i) {
				nuevasOperaciones[i] = "R";
			}
			operaciones = nuevasOperaciones;
		}
		return operaciones;
	}

	public Rubik2 operar(String operaciones) {
		operaciones = operaciones.replace("  ", " ").trim();
		for (String operacion : operaciones.split(" ")) {
			rotar(operacion);
		}
		return this;
	}

	public Rubik2 rotar(String[] operaciones) {
		for (String operacion : operaciones) {
			rotar(operacion);
		}
		return this;
	}

	public Rubik2 rotar(String operacion) {
		Rubik2 rubik2 = null;
		rubik2 = "R".equals(operacion) ? R() : rubik2;
		rubik2 = "R2".equals(operacion) ? R2() : rubik2;
		rubik2 = "R'".equals(operacion) ? R3() : rubik2;
		rubik2 = "U".equals(operacion) ? U() : rubik2;
		rubik2 = "U2".equals(operacion) ? U2() : rubik2;
		rubik2 = "U'".equals(operacion) ? U3() : rubik2;
		rubik2 = "F".equals(operacion) ? F() : rubik2;
		rubik2 = "F2".equals(operacion) ? F2() : rubik2;
		rubik2 = "F'".equals(operacion) ? F3() : rubik2;
		return rubik2;
	}

	public Rubik2 desarmar() {
		for (int i = 0; i < 100; ++i) {
			String operacion = XRandom.randomFrom(operaciones);
			rotar(operacion);
		}
		return this;
	}

	/* ========== OPERACIONES ========== */
	public Rubik2 R() {
		cache[0] = estado[0][1][1];
		cache[1] = estado[0][0][1];
		cache[2] = estado[4][0][0];
		cache[3] = estado[4][1][0];
		cache[4] = estado[5][1][1];
		cache[5] = estado[5][0][1];
		cache[6] = estado[1][1][1];
		cache[7] = estado[1][0][1];
		estado[4][0][0] = cache[0];
		estado[4][1][0] = cache[1];
		estado[5][1][1] = cache[2];
		estado[5][0][1] = cache[3];
		estado[1][1][1] = cache[4];
		estado[1][0][1] = cache[5];
		estado[0][1][1] = cache[6];
		estado[0][0][1] = cache[7];
		cache[0] = estado[2][0][0];
		cache[1] = estado[2][0][1];
		cache[2] = estado[2][1][1];
		cache[3] = estado[2][1][0];
		estado[2][0][1] = cache[0];
		estado[2][1][1] = cache[1];
		estado[2][1][0] = cache[2];
		estado[2][0][0] = cache[3];
		return this;
	}

	public Rubik2 R2() {
		cache[0] = estado[0][1][1];
		cache[1] = estado[0][0][1];
		cache[2] = estado[4][0][0];
		cache[3] = estado[4][1][0];
		cache[4] = estado[5][1][1];
		cache[5] = estado[5][0][1];
		cache[6] = estado[1][1][1];
		cache[7] = estado[1][0][1];
		estado[5][1][1] = cache[0];
		estado[5][0][1] = cache[1];
		estado[1][1][1] = cache[2];
		estado[1][0][1] = cache[3];
		estado[0][1][1] = cache[4];
		estado[0][0][1] = cache[5];
		estado[4][0][0] = cache[6];
		estado[4][1][0] = cache[7];
		cache[0] = estado[2][0][0];
		cache[1] = estado[2][0][1];
		cache[2] = estado[2][1][1];
		cache[3] = estado[2][1][0];
		estado[2][1][1] = cache[0];
		estado[2][1][0] = cache[1];
		estado[2][0][0] = cache[2];
		estado[2][0][1] = cache[3];
		return this;
	}

	public Rubik2 R3() {
		cache[0] = estado[0][1][1];
		cache[1] = estado[0][0][1];
		cache[2] = estado[4][0][0];
		cache[3] = estado[4][1][0];
		cache[4] = estado[5][1][1];
		cache[5] = estado[5][0][1];
		cache[6] = estado[1][1][1];
		cache[7] = estado[1][0][1];
		estado[1][1][1] = cache[0];
		estado[1][0][1] = cache[1];
		estado[0][1][1] = cache[2];
		estado[0][0][1] = cache[3];
		estado[4][0][0] = cache[4];
		estado[4][1][0] = cache[5];
		estado[5][1][1] = cache[6];
		estado[5][0][1] = cache[7];
		cache[0] = estado[2][0][0];
		cache[1] = estado[2][0][1];
		cache[2] = estado[2][1][1];
		cache[3] = estado[2][1][0];
		estado[2][1][0] = cache[0];
		estado[2][0][0] = cache[1];
		estado[2][0][1] = cache[2];
		estado[2][1][1] = cache[3];
		return this;
	}

	public Rubik2 U() {
		cache[0] = estado[1][0][1];
		cache[1] = estado[1][0][0];
		cache[2] = estado[3][0][1];
		cache[3] = estado[3][0][0];
		cache[4] = estado[4][0][1];
		cache[5] = estado[4][0][0];
		cache[6] = estado[2][0][1];
		cache[7] = estado[2][0][0];
		estado[3][0][1] = cache[0];
		estado[3][0][0] = cache[1];
		estado[4][0][1] = cache[2];
		estado[4][0][0] = cache[3];
		estado[2][0][1] = cache[4];
		estado[2][0][0] = cache[5];
		estado[1][0][1] = cache[6];
		estado[1][0][0] = cache[7];
		cache[0] = estado[0][0][0];
		cache[1] = estado[0][0][1];
		cache[2] = estado[0][1][1];
		cache[3] = estado[0][1][0];
		estado[0][0][1] = cache[0];
		estado[0][1][1] = cache[1];
		estado[0][1][0] = cache[2];
		estado[0][0][0] = cache[3];
		return this;
	}

	public Rubik2 U2() {
		cache[0] = estado[1][0][1];
		cache[1] = estado[1][0][0];
		cache[2] = estado[3][0][1];
		cache[3] = estado[3][0][0];
		cache[4] = estado[4][0][1];
		cache[5] = estado[4][0][0];
		cache[6] = estado[2][0][1];
		cache[7] = estado[2][0][0];
		estado[4][0][1] = cache[0];
		estado[4][0][0] = cache[1];
		estado[2][0][1] = cache[2];
		estado[2][0][0] = cache[3];
		estado[1][0][1] = cache[4];
		estado[1][0][0] = cache[5];
		estado[3][0][1] = cache[6];
		estado[3][0][0] = cache[7];
		cache[0] = estado[0][0][0];
		cache[1] = estado[0][0][1];
		cache[2] = estado[0][1][1];
		cache[3] = estado[0][1][0];
		estado[0][1][1] = cache[0];
		estado[0][1][0] = cache[1];
		estado[0][0][0] = cache[2];
		estado[0][0][1] = cache[3];
		return this;
	}

	public Rubik2 U3() {
		cache[0] = estado[1][0][1];
		cache[1] = estado[1][0][0];
		cache[2] = estado[3][0][1];
		cache[3] = estado[3][0][0];
		cache[4] = estado[4][0][1];
		cache[5] = estado[4][0][0];
		cache[6] = estado[2][0][1];
		cache[7] = estado[2][0][0];
		estado[2][0][1] = cache[0];
		estado[2][0][0] = cache[1];
		estado[1][0][1] = cache[2];
		estado[1][0][0] = cache[3];
		estado[3][0][1] = cache[4];
		estado[3][0][0] = cache[5];
		estado[4][0][1] = cache[6];
		estado[4][0][0] = cache[7];
		cache[0] = estado[0][0][0];
		cache[1] = estado[0][0][1];
		cache[2] = estado[0][1][1];
		cache[3] = estado[0][1][0];
		estado[0][1][0] = cache[0];
		estado[0][0][0] = cache[1];
		estado[0][0][1] = cache[2];
		estado[0][1][1] = cache[3];
		return this;
	}

	public Rubik2 F() {
		cache[0] = estado[2][0][0];
		cache[1] = estado[2][1][0];
		cache[2] = estado[5][0][1];
		cache[3] = estado[5][0][0];
		cache[4] = estado[3][1][1];
		cache[5] = estado[3][0][1];
		cache[6] = estado[0][1][0];
		cache[7] = estado[0][1][1];
		estado[5][0][1] = cache[0];
		estado[5][0][0] = cache[1];
		estado[3][1][1] = cache[2];
		estado[3][0][1] = cache[3];
		estado[0][1][0] = cache[4];
		estado[0][1][1] = cache[5];
		estado[2][0][0] = cache[6];
		estado[2][1][0] = cache[7];
		cache[0] = estado[1][0][0];
		cache[1] = estado[1][0][1];
		cache[2] = estado[1][1][1];
		cache[3] = estado[1][1][0];
		estado[1][0][1] = cache[0];
		estado[1][1][1] = cache[1];
		estado[1][1][0] = cache[2];
		estado[1][0][0] = cache[3];
		return this;
	}

	public Rubik2 F2() {
		cache[0] = estado[2][0][0];
		cache[1] = estado[2][1][0];
		cache[2] = estado[5][0][1];
		cache[3] = estado[5][0][0];
		cache[4] = estado[3][1][1];
		cache[5] = estado[3][0][1];
		cache[6] = estado[0][1][0];
		cache[7] = estado[0][1][1];
		estado[3][1][1] = cache[0];
		estado[3][0][1] = cache[1];
		estado[0][1][0] = cache[2];
		estado[0][1][1] = cache[3];
		estado[2][0][0] = cache[4];
		estado[2][1][0] = cache[5];
		estado[5][0][1] = cache[6];
		estado[5][0][0] = cache[7];
		cache[0] = estado[1][0][0];
		cache[1] = estado[1][0][1];
		cache[2] = estado[1][1][1];
		cache[3] = estado[1][1][0];
		estado[1][1][1] = cache[0];
		estado[1][1][0] = cache[1];
		estado[1][0][0] = cache[2];
		estado[1][0][1] = cache[3];
		return this;
	}

	public Rubik2 F3() {
		cache[0] = estado[2][0][0];
		cache[1] = estado[2][1][0];
		cache[2] = estado[5][0][1];
		cache[3] = estado[5][0][0];
		cache[4] = estado[3][1][1];
		cache[5] = estado[3][0][1];
		cache[6] = estado[0][1][0];
		cache[7] = estado[0][1][1];
		estado[0][1][0] = cache[0];
		estado[0][1][1] = cache[1];
		estado[2][0][0] = cache[2];
		estado[2][1][0] = cache[3];
		estado[5][0][1] = cache[4];
		estado[5][0][0] = cache[5];
		estado[3][1][1] = cache[6];
		estado[3][0][1] = cache[7];
		cache[0] = estado[1][0][0];
		cache[1] = estado[1][0][1];
		cache[2] = estado[1][1][1];
		cache[3] = estado[1][1][0];
		estado[1][1][0] = cache[0];
		estado[1][0][0] = cache[1];
		estado[1][0][1] = cache[2];
		estado[1][1][1] = cache[3];
		return this;
	}

	/* ========== TOSTRING ========== */
	public static String color(int codigo) {
		String color = "-";
		color = codigo == 0 ? "B" : color;
		color = codigo == 1 ? "V" : color;
		color = codigo == 2 ? "R" : color;
		color = codigo == 3 ? "N" : color;
		color = codigo == 4 ? "Z" : color;
		color = codigo == 5 ? "A" : color;
		return color;
	}

	public static String toString(int[][][] estado) {
		StringBuilder sb = new StringBuilder();

		sb.append("   ");
		sb.append(color(estado[0][0][0]));
		sb.append(color(estado[0][0][1]));
		sb.append("\n");

		sb.append("   ");
		sb.append(color(estado[0][1][0]));
		sb.append(color(estado[0][1][1]));
		sb.append("\n");

		sb.append(color(estado[3][0][0]));
		sb.append(color(estado[3][0][1]));
		sb.append(" ");
		sb.append(color(estado[1][0][0]));
		sb.append(color(estado[1][0][1]));
		sb.append(" ");
		sb.append(color(estado[2][0][0]));
		sb.append(color(estado[2][0][1]));
		sb.append(" ");
		sb.append(color(estado[4][0][0]));
		sb.append(color(estado[4][0][1]));
		sb.append("\n");

		sb.append(color(estado[3][1][0]));
		sb.append(color(estado[3][1][1]));
		sb.append(" ");
		sb.append(color(estado[1][1][0]));
		sb.append(color(estado[1][1][1]));
		sb.append(" ");
		sb.append(color(estado[2][1][0]));
		sb.append(color(estado[2][1][1]));
		sb.append(" ");
		sb.append(color(estado[4][1][0]));
		sb.append(color(estado[4][1][1]));
		sb.append("\n");

		sb.append("   ");
		sb.append(color(estado[5][0][0]));
		sb.append(color(estado[5][0][1]));
		sb.append("\n");

		sb.append("   ");
		sb.append(color(estado[5][1][0]));
		sb.append(color(estado[5][1][1]));
		sb.append("\n");

		return sb.toString();
	}

	public String toString() {
		return toString(estado);
	}
}
