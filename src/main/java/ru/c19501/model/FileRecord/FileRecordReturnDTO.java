package ru.c19501.model.FileRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileRecordReturnDTO {
    String fileName;
    String fileType;
    String creationDate;
    int volumeInBlocks;
}
