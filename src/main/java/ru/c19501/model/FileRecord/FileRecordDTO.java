package ru.c19501.model.FileRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*
* DTO for storing Json results of queries to core
*
* */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileRecordDTO {
    String fileName;
    String fileType;
    int volumeInBlocks;
    String creationDate;
    String id;
    boolean deleted;
}
