package ar.gabrielsuarez.glib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import ar.gabrielsuarez.glib.core.Convert;
import ar.gabrielsuarez.glib.core.Crypto;
import ar.gabrielsuarez.glib.core.Enviroment;
import ar.gabrielsuarez.glib.core.Reflection;
import ar.gabrielsuarez.glib.core.Resource;
import ar.gabrielsuarez.glib.core.Serializer;
import ar.gabrielsuarez.glib.core.XCollection;
import ar.gabrielsuarez.glib.core.XDate;
import ar.gabrielsuarez.glib.core.XException;
import ar.gabrielsuarez.glib.core.XFile;
import ar.gabrielsuarez.glib.core.XRandom;
import ar.gabrielsuarez.glib.core.XStream;
import ar.gabrielsuarez.glib.core.XString;

public abstract class G {

	/* ========== CONVERT ========== */
	public static <T> T cast(Class<T> type, Object... values) {
		return Convert.cast(type, values);
	}
	
	public static Boolean toBoolean(Object... values) {
		return Convert.toBoolean(values);
	}

	public static Short toShort(Object... values) {
		return Convert.toShort(values);
	}

	public static Integer toInteger(Object... values) {
		return Convert.toInteger(values);
	}

	public static Long toLong(Object... values) {
		return Convert.toLong(values);
	}

	public static Float toFloat(Object... values) {
		return Convert.toFloat(values);
	}

	public static Double toDouble(Object... values) {
		return Convert.toDouble(values);
	}

	public static BigInteger toBigInteger(Object... values) {
		return Convert.toBigInteger(values);
	}

	public static BigDecimal toBigDecimal(Object... values) {
		return Convert.toBigDecimal(values);
	}
	
	public static Date toDate(Object... values) {
		return Convert.toDate(values);
	}

	public static java.sql.Date toSqlDate(Object... values) {
		return Convert.toSqlDate(values);
	}

	public static LocalDate toLocalDate(Object... values) {
		return Convert.toLocalDate(values);
	}

	public static LocalDateTime toLocalDateTime(Object... values) {
		return Convert.toLocalDateTime(values);
	}

	/* ========== CRYPTO ========== */
	public static String md2(String value) {
		return Crypto.md2(value);
	}

	public static String md5(String value) {
		return Crypto.md5(value);
	}

	public static String sha(String value) {
		return Crypto.sha(value);
	}

	public static String sha224(String value) {
		return Crypto.sha224(value);
	}

	public static String sha256(String value) {
		return Crypto.sha256(value);
	}

	public static String sha384(String value) {
		return Crypto.sha384(value);
	}

	public static String sha512(String value) {
		return Crypto.sha512(value);
	}

	public static String sha512_244(String value) {
		return Crypto.sha512_244(value);
	}

	public static String sha512_256(String value) {
		return Crypto.sha512_256(value);
	}

	/* ========== ENVIROMENT ========== */
	public static Boolean isWindows() {
		return Enviroment.isWindows();
	}

	public static Boolean isJar() {
		return Enviroment.isJar();
	}

	public static Boolean isDocker() {
		return Enviroment.isDocker();
	}

	public static String desktopPath() {
		return Enviroment.desktopPath();
	}

	public static String desktopPath(String relativePath) {
		return Enviroment.desktopPath(relativePath);
	}

	public static String compiledPath() {
		return Enviroment.compiledPath();
	}

	public static String sourcePath() {
		return Enviroment.sourcePath();
	}

	public static String sourcePath(String relativePath) {
		return Enviroment.sourcePath(relativePath);
	}

	/* ========== REFLECTION ========== */
	public static <T> T instance(Class<T> type, Object... parameters) {
		return Reflection.instance(type, parameters);
	}

	public static Class<?>[] types(Object... values) {
		return Reflection.types(values);
	}

	public static Field[] fields(Object object) {
		return Reflection.fields(object);
	}

	public static Field[] fields(Class<?> type) {
		return Reflection.fields(type);
	}

	public static <T> Map<String, Field> fieldMap(Class<T> type) {
		return Reflection.fieldMap(type);
	}

	public static <T> T trimAllFields(T object) {
		return Reflection.trimAllFields(object);
	}

	public static <T extends Iterable<?>> T trimAllFields(T objects) {
		return Reflection.trimAllFields(objects);
	}

	/* ========== RESOURCE ========== */
	public static InputStream resourceInputStream(String path) {
		return Resource.resourceInputStream(path);
	}

	public static byte[] resourceBytes(String path) {
		return Resource.resourceBytes(path);
	}

	public static String resourceBase64(String path) {
		return Resource.resourceBase64(path);
	}

	public static Properties properties(String path) {
		return Resource.properties(path);
	}

	public static Map<String, String> propertiesToMap(String path) {
		return Resource.propertiesToMap(path);
	}

	/* ========== SERIALIZER ========== */
	public static <T> T fromMap(Map<String, Object> map, Class<T> type) {
		return Serializer.fromMap(map, type);
	}

	public static Map<String, Object> toMap(Object object) {
		return Serializer.toMap(object);
	}

	public static Object fromJson(String json) {
		return Serializer.fromJson(json);
	}

	public static <T> T fromJson(String json, Class<T> type) {
		return Serializer.fromJson(json, type);
	}

	public static String toJson(Object object) {
		return Serializer.toJson(object);
	}

	public static String toJsonSingleLine(Object object) {
		return Serializer.toJsonSingleLine(object);
	}

	public static Object fromXml(String xml) {
		return Serializer.fromXml(xml);
	}

	public static <T> T fromXml(String xml, Class<T> type) {
		return Serializer.fromXml(xml, type);
	}

	public static String toXml(Object object, String root) {
		return Serializer.toXml(object, root);
	}

	public static String toXmlSingleLine(Object object, String root) {
		return Serializer.toXmlSingleLine(object, root);
	}

	public static Object fromYaml(String yaml) {
		return Serializer.fromYaml(yaml);
	}

	public static <T> T fromYaml(String yaml, Class<T> type) {
		return Serializer.fromYaml(yaml, type);
	}

	public static String toYaml(Object object) {
		return Serializer.toYaml(object);
	}

	public static byte[] toBytes(Serializable object) {
		return Serializer.toBytes(object);
	}

	public static <T> T toClass(Class<T> type, byte[] bytes) {
		return Serializer.toClass(type, bytes);
	}

	public static Object toObject(byte[] bytes) {
		return Serializer.toObject(bytes);
	}

	/* ========== XCOLLECTION ========== */
	public static <T> T add(Collection<T> collection, T value) {
		return XCollection.add(collection, value);
	}

	public static <T> T add(Collection<T> collection, T value, Boolean condition) {
		return XCollection.add(collection, value, condition);
	}

	@SafeVarargs
	public static <T extends Comparable<T>> T min(T... values) {
		return XCollection.min(values);
	}

	@SafeVarargs
	public static <T extends Comparable<T>> T max(T... values) {
		return XCollection.max(values);
	}

	public static Boolean isEmpty(Object value) {
		return XCollection.isEmpty(value);
	}

	public static Boolean anyEmpty(Object... values) {
		return XCollection.anyEmpty(values);
	}

	public static Boolean allEmpty(Object... values) {
		return XCollection.allEmpty(values);
	}

	@SafeVarargs
	public static <T> T firstNonEmpty(T... values) {
		return XCollection.firstNonEmpty(values);
	}

	@SafeVarargs
	public static <T> T lastNonEmpty(T... values) {
		return XCollection.lastNonEmpty(values);
	}

	/* ========== XDATE ========== */
	public static String dateMask(String value) {
		return XDate.dateMask(value);
	}

	public static String dateFormat(String value) {
		return XDate.dateFormat(value);
	}

	/* ========== XEXCEPTION ========== */
	public static RuntimeException runtimeException(Throwable t) {
		return XException.runtimeException(t);
	}

	public static Throwable getCause(Throwable t) {
		return XException.getCause(t);
	}

	public static StackTraceElement stackTraceElement(Throwable t, String packageName) {
		return XException.stackTraceElement(t, packageName);
	}

	public static String toString(Exception e) {
		return XException.toString(e);
	}

	/* ========== XFILE ========== */
	public static Boolean fileExists(String path) {
		return XFile.fileExists(path);
	}

	public static String readFile(String path) {
		return XFile.readFile(path);
	}

	public static File writeFile(String path, String content) {
		return XFile.writeFile(path, content);
	}

	public static File writeFile(String basePath, String relativePath, String content) {
		return XFile.writeFile(basePath, relativePath, content);
	}

	public static File writeFile(String path, byte[] content) {
		return XFile.writeFile(path, content);
	}

	public static File writeFile(String basePath, String relativePath, byte[] content) {
		return XFile.writeFile(basePath, relativePath, content);
	}

	/* ========== XRANDOM ========== */
	public static Integer randomInt(Integer min, Integer max) {
		return XRandom.randomInt(min, max);
	}

	@SafeVarargs
	public static <T> T randomFrom(T... values) {
		return XRandom.randomFrom(values);
	}

	/* ========== XSTREAM ========== */
	public static byte[] toBytes(InputStream inputStream) {
		return XStream.toBytes(inputStream);
	}

	public static String toString(InputStream inputStream) {
		return XStream.toString(inputStream);
	}

	public static ByteArrayOutputStream toByteArrayOutputStream(InputStream inputStream) {
		return XStream.toByteArrayOutputStream(inputStream);
	}

	/* ========== XSTRING ========== */
	public static String toHex(byte[] bytes) {
		return XString.toHex(bytes);
	}

	public static String camelCase(String value) {
		return XString.camelCase(value);
	}

	public static String pascalCase(String value) {
		return XString.pascalCase(value);
	}

	public static String urlEncode(String value) {
		return XString.urlEncode(value);
	}

	public static String base64(String value) {
		return XString.base64(value);
	}

	public static String base64(byte[] bytes) {
		return XString.base64(bytes);
	}
}
