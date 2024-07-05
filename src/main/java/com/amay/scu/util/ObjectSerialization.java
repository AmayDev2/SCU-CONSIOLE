package com.amay.scu.util;

import com.amay.scu.model.SLELocationListObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Map;

public class ObjectSerialization {

    public static String objectToJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save a JSON string to a file
    public static void saveJsonToFile(String json, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(json);
            System.out.println("JSON data is saved in " + filename);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // Fetch a JSON string from a file and convert it to an object
    public static <T> T jsonFromFile(String filename, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileReader fileReader = new FileReader(filename)) {
            return objectMapper.readValue(fileReader, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}