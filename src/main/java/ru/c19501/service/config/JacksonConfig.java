package ru.c19501.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JacksonConfig {

    static ObjectMapper createObjectMapper(){
        return new ObjectMapper();
    }

}
