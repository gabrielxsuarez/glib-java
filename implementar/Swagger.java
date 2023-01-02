package ar.com.gabrielxsuarez.nucleo.metadata.swagger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Swagger {
    public String swagger;
    public Info info;
    public String host;
    public String basePath;
    public List<String> schemes;
    public List<String> consumes;
    public List<String> produces;
    public Map<String, PathItem> paths;
    public Map<String, Schema> definitions;
    public Map<String, Parameter> parameters;
    public Map<String, Response> responses;
    public Map<String, SecurityScheme> securityDefinitions;
    public List<Map<String, List<String>>> security;
    public List<Tag> tags;
    public ExternalDocumentation externalDocs;

    public class Info {
        public String title;
        public String description;
        public String termsOfService;
        public Contact contact;
        public License license;
        public String version;
    }

    public class Contact {
        public String name;
        public String url;
        public String email;
    }

    public class License {
        public String name;
        public String url;
    }

    public class PathItem {
        public String $ref;
        public Operation get;
        public Operation put;
        public Operation post;
        public Operation delete;
        public Operation options;
        public Operation head;
        public Operation patch;
        public List<Parameter> parameters;
    }

    public class Operation {
        public List<String> tags;
        public String summary;
        public String description;
        public ExternalDocumentation externalDocs;
        public String operationId;
        public List<String> consumes;
        public List<String> produces;
        public List<Parameter> parameters;
        public Map<String, Response> responses;
        public List<String> schemes;
        public Boolean deprecated;
        public List<Map<String, List<String>>> security;
    }

    public class ExternalDocumentation {
        public String description;
        public String url;
    }

    public class Parameter {
        public String $ref;
        public String name;
        public String in;
        public String description;
        public Boolean required;
        public Schema schema;
        public String type;
        public String format;
        public Boolean allowEmptyValue;
        public Items items;
        public String collectionFormat;
        public Object _default;
        public BigDecimal maximum;
        public Boolean exclusiveMaximum;
        public BigDecimal minimum;
        public Boolean exclusiveMinimum;
        public Integer maxLength;
        public Integer minLength;
        public String pattern;
        public Integer maxItems;
        public Integer minItems;
        public Boolean uniqueItems;
        public List<Object> _enum;
        public BigDecimal multipleOf;
    }

    public class Items {
        public String type;
        public String format;
        public Items Items;
        public String collectionFormat;
        public Object _default;
        public BigDecimal maximum;
        public Boolean exclusiveMaximum;
        public BigDecimal minimum;
        public Boolean exclusiveMinimum;
        public Integer maxLength;
        public Integer minLength;
        public String pattern;
        public Integer maxItems;
        public Integer minItems;
        public Boolean uniqueItems;
        public List<Object> _enum;
        public BigDecimal multipleOf;
    }

    public class Response {
        public String $ref;
        public String description;
        public Schema schema;
        public Map<String, Header> headers;
        public Map<String, Object> examples;
    }

    public class Header {
        public String description;
        public String type;
        public String format;
        public Items items;
        public String collectionFormat;
        public Object _default;
        public BigDecimal maximum;
        public Boolean exclusiveMaximum;
        public BigDecimal minimum;
        public Boolean exclusiveMinimum;
        public Integer maxLength;
        public Integer minLength;
        public String pattern;
        public Integer maxItems;
        public Integer minItems;
        public Boolean uniqueItems;
        public List<Object> _enum;
        public BigDecimal multipleOf;
    }

    public class Tag {
        public String name;
        public String description;
        public ExternalDocumentation externalDocs;
    }

    public class Schema {
        public String $ref;
        public String format;
        public String title;
        public String description;
        public Object _default;
        public BigDecimal multipleOf;
        public BigDecimal maximum;
        public Boolean exclusiveMaximum;
        public BigDecimal minimum;
        public Boolean exclusiveMinimum;
        public Integer maxLength;
        public Integer minLength;
        public String pattern;
        public Integer maxItems;
        public Integer minItems;
        public Boolean uniqueItems;
        public Integer maxProperties;
        public Integer minProperties;
        public List<String> required;
        public List<Object> _enum;
        public String type;
        public Schema items;
        public List<Schema> allOf;
        public Map<String, Schema> properties;
        public Schema additionalProperties;
        public String discriminator;
        public Boolean readOnly;
        public Xml xml;
        public ExternalDocumentation externalDocs;
        public Object example;
    }

    public class Xml {
        public String name;
        public String namespace;
        public String prefix;
        public Boolean attribute;
        public Boolean wrapped;
    }

    public class SecurityScheme {
        public String type;
        public String description;
        public String name;
        public String in;
        public String flow;
        public String authorizationUrl;
        public String tokenUrl;
        public Map<String, String> scopes;
    }
}
