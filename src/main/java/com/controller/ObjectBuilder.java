package com.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

import com.entity.Hospital;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.utility.DateDeserializer;

public class ObjectBuilder {

	public static Object buildObject(Class<?> clazz, String file)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		Gson gson = gsonBuilder.create();
		return gson.fromJson(new FileReader("./src/test/resources/" + file), clazz);
	}
}
