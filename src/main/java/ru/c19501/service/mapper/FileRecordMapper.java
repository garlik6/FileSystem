package ru.c19501.service.mapper;

import ru.c19501.model.FileRecord.FileRecordDTO;
import ru.c19501.model.FileRecord.FileRecordReturnDTO;

public abstract class FileRecordMapper {

    public static FileRecordReturnDTO dtoToReturnDto(FileRecordDTO dto){
        return FileRecordReturnDTO.builder()
                .fileName(dto.getFileName())
                .fileType(dto.getFileType())
                .creationDate(dto.getCreationDate())
                .volumeInBlocks(dto.getVolumeInBlocks())
                .build();
    }

}
