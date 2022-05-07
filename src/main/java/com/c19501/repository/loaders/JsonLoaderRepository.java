package com.c19501.repository.loaders;

import com.c19501.repository.LoaderRepository;
import com.c19501.repository.Repository;

import com.c19501.repository.repositories.JsonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonLoaderRepository implements LoaderRepository {

    @Override
    public Repository loadRepository() {
        JsonRepository jsonRepository = new JsonRepository();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue( new File(jsonRepository.getSystemRepository() + '/' + jsonRepository.getSystemFileName()), JsonRepository.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonRepository;
    }
}
