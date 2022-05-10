package ru.c19501.core.repository.loaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.c19501.core.files.Views;
import ru.c19501.core.repository.RepoLoader;
import ru.c19501.core.repository.Repository;
import ru.c19501.core.repository.repositories.JsonRepository;

import java.io.File;
import java.io.IOException;

public class JsonLoaderRepository implements RepoLoader {

    @Override
    public Repository loadRepository() throws IOException {
        JsonRepository jsonRepository = new JsonRepository();
        ObjectMapper objectMapper = new ObjectMapper();
            jsonRepository = objectMapper.readerWithView(Views.Internal.class).readValue(new File(jsonRepository.getSystemRepository() + '/' + jsonRepository.getSystemFileName()), JsonRepository.class);
        return jsonRepository;
    }
}
