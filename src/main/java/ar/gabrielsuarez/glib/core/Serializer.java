package ar.gabrielsuarez.glib.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.gabrielsuarez.glib.G;

public abstract class Serializer {

	/* ========== ATTRIBUTES ========== */
	protected static JsonMapper jsonMapper = new JsonMapper();
	protected static XmlMapper xmlMapper = new XmlMapper();
	protected static YAMLMapper yamlMapper = new YAMLMapper();

	/* ========== INIT ========== */
	static {
		configure(jsonMapper);
		configure(xmlMapper);
		configure(yamlMapper);
	}

	protected static void configure(ObjectMapper mapper) {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(module());
	}

	protected static SimpleModule module() {
		SimpleModule simpleModule = new SimpleModule();
//		simpleModule.addSerializer(Data.class, new DataSerializer());
		return simpleModule;
	}

	/* ========== MAP ========== */
	public static <T> T fromMap(Map<String, Object> map, Class<T> type) {
		return fromJson(toJson(map), type);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object object) {
		return (Map<String, Object>) fromJson(toJson(object), Map.class);
	}

	/* ========== JSON ========== */
	public static Object fromJson(String json) {
		return fromJson(json, Object.class);
	}

	public static <T> T fromJson(String json, Class<T> type) {
		try {
			return jsonMapper.readValue(json, type);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	public static String toJson(Object object) {
		try {
			return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	public static String toJsonSingleLine(Object object) {
		try {
			return jsonMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	/* ========== XML ========== */
	public static Object fromXml(String xml) {
		return fromXml(xml, Object.class);
	}

	public static <T> T fromXml(String xml, Class<T> type) {
		try {
			return xmlMapper.readValue(xml, type);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	public static String toXml(Object object, String root) {
		try {
			return xmlMapper.writerWithDefaultPrettyPrinter().withRootName(root).writeValueAsString(object);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	public static String toXmlSingleLine(Object object, String root) {
		try {
			return xmlMapper.writer().withRootName(root).writeValueAsString(object);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	/* ========== YAML ========== */
	public static Object fromYaml(String yaml) {
		return fromYaml(yaml, Object.class);
	}

	public static <T> T fromYaml(String yaml, Class<T> type) {
		try {
			return yamlMapper.readValue(yaml, type);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	public static String toYaml(Object object) {
		try {
			return yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	/* ========== SERIALIZABLE ========== */
	public static byte[] toBytes(Serializable object) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			byte[] data = bos.toByteArray();
			return data;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T toClass(Class<T> type, byte[] bytes) {
		return (T) toObject(bytes);
	}

	public static Object toObject(byte[] bytes) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream is = new ObjectInputStream(in);
			Object object = is.readObject();
			return object;
		} catch (Exception e) {
			throw G.runtimeException(e);
		}
	}

	/* ========== CUSTOM SERIALIZER ========== */
//	private static class DataSerializer extends JsonSerializer<Data> {
//		public void serialize(Data value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//			Object object = value.object();
//			serializers.findValueSerializer(object.getClass()).serialize(object, gen, serializers);
//		}
//	}

	/* ========== EXCEPTIONS ========== */
	public static class SerializerException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public SerializerException(Exception e) {
			super(e);
		}
	}
}