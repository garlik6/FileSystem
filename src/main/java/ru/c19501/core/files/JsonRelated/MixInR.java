package ru.c19501.core.files.JsonRelated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(value = {"systemVersion", "owner", "systemFileName", "systemRepository"})
public abstract class MixInR {
}
