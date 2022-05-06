package com.c19501.filesystem.repository.repoVariants;

import com.c19501.filesystem.repository.RepoLoader;
import com.c19501.filesystem.repository.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonLoader extends RepoLoader {

    @Override
    public Repository loadRepository() {
        JsonRepository jsonRepository = new JsonRepository();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue( new File(jsonRepository.getSystemRepository() + "/" + jsonRepository.getSystemFileName()), JsonRepository.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonRepository;
    }
}
