package ar.gabrielsuarez.glib.data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class DataFile {

	/* ========== ATRIBUTOS ========== */
	public String path;
	public String name;
	public byte[] bytes;

	/* ========== CONSTRUCTORES ========== */
	public DataFile(String path) {
		try {
			Path file = Paths.get(path);
			if (file != null) {
				Path fileName = file.getFileName();
				this.path = file.toAbsolutePath().toString();
				if (fileName != null) {
					this.name = fileName.toString();
				}
				this.bytes = Files.readAllBytes(file);
			}
		} catch (Exception e) {
			throw new DataFileException(e);
		}
	}

	public DataFile(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
	}

	public DataFile(String nombre, String base64) {
		this.name = nombre;
		this.bytes = Base64.getDecoder().decode(base64);
	}

	/* ========== METODOS ========== */
	public String string() {
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public String extension() {
		return extension(name);
	}

	public static String extension(String nombreArchivo) {
		return extension(nombreArchivo, false);
	}

	public static String extension(String nombreArchivo, Boolean mantenerPunto) {
		if (nombreArchivo != null) {
			int i = nombreArchivo.lastIndexOf('.');
			if (i >= 0) {
				String extension = nombreArchivo.substring(i + (mantenerPunto ? 0 : 1)).trim();
				return extension;
			}
		}
		return "";
	}

	public String base64() {
		String base64 = Base64.getEncoder().encodeToString(bytes);
		return base64;
	}

	public static Boolean guardar(String base64, String path) {
		File outputFile = new File(path);
		try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
			outputStream.write(Base64.getDecoder().decode(base64));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void escribir(String ruta, String contenido) {
		new File(ruta).getParentFile().mkdirs();
		try (PrintWriter archivo = new PrintWriter(ruta)) {
			archivo.write(contenido);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void escribir(String ruta, byte[] contenido) {
		try (FileOutputStream fos = new FileOutputStream(ruta)) {
			fos.write(contenido);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public DataFile comprimirImagen(Integer calidad) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension().toLowerCase());
			ImageWriter writer = (ImageWriter) writers.next();

			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);

			ImageWriteParam parametros = writer.getDefaultWriteParam();
			if (parametros.canWriteCompressed()) {
				parametros.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				parametros.setCompressionQuality(calidad);
			}
			writer.write(null, new IIOImage(bufferedImage, null, null), parametros);

			byte[] imagenComprimida = os.toByteArray();
			return new DataFile(name, imagenComprimida);
		} catch (Exception e) {
			return this;
		}
	}

	/* ========== EXCEPTIONS ========== */
	public static class DataFileException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public DataFileException(Exception e) {
			super(e);
		}
	}
}
