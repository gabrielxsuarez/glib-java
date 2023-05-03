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
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

import ar.gabrielsuarez.glib.core.Convert;
import ar.gabrielsuarez.glib.core.XCollection;
import ar.gabrielsuarez.glib.core.XCrypto;
import ar.gabrielsuarez.glib.core.XDate;
import ar.gabrielsuarez.glib.core.XEnviroment;
import ar.gabrielsuarez.glib.core.XException;
import ar.gabrielsuarez.glib.core.XFile;
import ar.gabrielsuarez.glib.core.XRandom;
import ar.gabrielsuarez.glib.core.XReflection;
import ar.gabrielsuarez.glib.core.XResource;
import ar.gabrielsuarez.glib.core.XSerializer;
import ar.gabrielsuarez.glib.core.XStream;
import ar.gabrielsuarez.glib.core.XString;
import ar.gabrielsuarez.glib.serialization.Serializer;

public abstract class G {

	/* ========== CONVERT ========== */
	public static <T> T cast(Class<T> type, Object... values) {
		return Convert.cast(type, values);
	}

	public static String toString(Object... values) {
		return Convert.toString(values);
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

	/* ========== XCOLLECTION ========== */
	@SafeVarargs
	public static <T extends Object> List<T> listOf(T... values) {
		return XCollection.listOf(values);
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		return XCollection.setOf(values);
	}

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

	@SafeVarargs
	public static <T> T firstNonNull(T... values) {
		return XCollection.firstNonNull(values);
	}

	@SafeVarargs
	public static <T> T lastNonNull(T... values) {
		return XCollection.lastNonNull(values);
	}

	/* ========== XCRYPTO ========== */
	public static String md2(String value) {
		return XCrypto.md2(value);
	}

	public static String md5(String value) {
		return XCrypto.md5(value);
	}

	public static String sha(String value) {
		return XCrypto.sha(value);
	}

	public static String sha224(String value) {
		return XCrypto.sha224(value);
	}

	public static String sha256(String value) {
		return XCrypto.sha256(value);
	}

	public static String sha384(String value) {
		return XCrypto.sha384(value);
	}

	public static String sha512(String value) {
		return XCrypto.sha512(value);
	}

	public static String sha512_244(String value) {
		return XCrypto.sha512_244(value);
	}

	public static String sha512_256(String value) {
		return XCrypto.sha512_256(value);
	}

	/* ========== XDATE ========== */
	public static String toString(Date value) {
		return XDate.toString(value);
	}

	public static String toString(TemporalAccessor value) {
		return XDate.toString(value);
	}

	public static Boolean isDate(String value) {
		return XDate.isDate(value);
	}

	public static String dateMask(String value) {
		return XDate.dateMask(value);
	}

	public static String dateFormat(String value) {
		return XDate.dateFormat(value);
	}

	public static Boolean dateFormatContainsTime(String dateFormat) {
		return XDate.dateFormatContainsTime(dateFormat);
	}

	/* ========== XENVIROMENT ========== */
	public static Boolean isWindows() {
		return XEnviroment.isWindows();
	}

	public static Boolean isJar() {
		return XEnviroment.isJar();
	}

	public static Boolean isDocker() {
		return XEnviroment.isDocker();
	}

	public static String desktopPath() {
		return XEnviroment.desktopPath();
	}

	public static String desktopPath(String relativePath) {
		return XEnviroment.desktopPath(relativePath);
	}

	public static String compiledPath() {
		return XEnviroment.compiledPath();
	}

	public static String sourcePath() {
		return XEnviroment.sourcePath();
	}

	public static String sourcePath(String relativePath) {
		return XEnviroment.sourcePath(relativePath);
	}

	public static String resourcePath() {
		return XEnviroment.resourcePath();
	}

	public static String tmpPath() {
		return XEnviroment.tmpPath();
	}

	/* ========== XEXCEPTION ========== */
	public static RuntimeException runtimeException(Throwable t) {
		return XException.runtimeException(t);
	}

	public static Throwable getCause(Throwable t) {
		return XException.getCause(t);
	}

	public static StackTraceElement stackTraceFirstElement(Throwable t, String packageName) {
		return XException.stackTraceFirstElement(t, packageName);
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

	public static String uuid() {
		return XRandom.uuid();
	}

	/* ========== XREFLECTION ========== */
	public static <T> T instance(Class<T> type) {
		return XReflection.instance(type);
	}

	public static <T> T instance(Class<T> type, Object... parameters) {
		return XReflection.instance(type, parameters);
	}

	public static Class<?>[] types(Object... values) {
		return XReflection.types(values);
	}

	public static String javaType(Object value) {
		return XReflection.javaType(value);
	}

	public static Field[] fields(Object object) {
		return XReflection.fields(object);
	}

	public static Field[] fields(Class<?> type) {
		return XReflection.fields(type);
	}

	public static <T> Map<String, Field> fieldMap(Class<T> type) {
		return XReflection.fieldMap(type);
	}

	public static <T> T trimAllFields(T object) {
		return XReflection.trimAllFields(object);
	}

	public static <T extends Iterable<?>> T trimAllFields(T objects) {
		return XReflection.trimAllFields(objects);
	}

	public static <T> T clone(T object) {
		return XReflection.clone(object);
	}

	public static <T> Boolean equals(T object1, T object2) {
		return XReflection.equals(object1, object2);
	}

	public static Integer hashCode(Object object) {
		return XReflection.hashCode(object);
	}

	/* ========== XRESOURCE ========== */
	public static InputStream resourceInputStream(String path) {
		return XResource.resourceInputStream(path);
	}

	public static byte[] resourceBytes(String path) {
		return XResource.resourceBytes(path);
	}

	public static String resourceBase64(String path) {
		return XResource.resourceBase64(path);
	}

	public static Properties properties(String path) {
		return XResource.properties(path);
	}

	public static Map<String, String> propertiesToMap(String path) {
		return XResource.propertiesToMap(path);
	}

	/* ========== XSERIALIZER ========== */
	public static Boolean likeJson(String value) {
		return XSerializer.likeJson(value);
	}

	public static Boolean likeXml(String value) {
		return XSerializer.likeXml(value);
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
	public static Boolean isBlank(Character character) {
		return XString.isBlank(character);
	}

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

	/* ========== SERIALIZER ========== */
	public static <T> void addSerializer(Class<? extends T> type, JsonSerializer<T> serializer) {
		Serializer.addSerializer(type, serializer);
	}

	public static <T> void addDeserializer(Class<T> type, JsonDeserializer<? extends T> deserializer) {
		Serializer.addDeserializer(type, deserializer);
	}

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
}
