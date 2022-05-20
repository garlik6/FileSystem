package ru.c19501.core.files.JsonRelated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = {"id", "creationDate"})
abstract public class MixInFR {
}
