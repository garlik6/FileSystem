package ru.c19501.core.repository;

import java.io.IOException;

public interface RepoLoader {
    Repository loadRepository() throws IOException;

    Repository loadByName(String name) throws IOException;

    Repository getCopy(Repository repository);
}
