package com.vwfsag.fso.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author qngl
 *
 */
public class JsonUtils {

	public static JsonNode fromJsonString(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (StringUtils.isNotBlank(jsonString)) {
				return mapper.readTree(jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toJsonString(JsonNode jsonNode) {
		if (jsonNode != null) {
			ObjectMapper mapper = new ObjectMapper();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				mapper.writeValue(os, jsonNode);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return os.toString();
		}
		return null;
	}

}
