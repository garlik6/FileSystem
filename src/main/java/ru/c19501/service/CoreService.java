package ru.c19501.service;

import ru.c19501.model.FileRecord.FileRecordReturnDTO;
import java.util.List;

/**
 * @author ������� �������
 * @version 1.0
 */
public interface CoreService {

    /**
     * ����� ��� �������� �����
     *
     * @param name ��� �����
     * @param type ���������� ����� (��� �����)
     * @param length ������ �����
     * @return true, ���� ���� ��� ������, ����� false
     */
    boolean createFile(String name, String type, int length);


    boolean addInfoToFile(String name, String type, int length);


    /**
     * ����� ��� ����, ����� ������ ��� �������� � ������� �����
     *
     * @return ���������� �������� ���������� � ���� ������ � �������, ���� ������ ���, �� �������� ������ ����
     */
    List<FileRecordReturnDTO> readFiles();

    /**
     * ����� ��� �������� �����
     *
     * @param name ��� �����
     * @param type ���������� ����� (��� �����)
     * @return true, ���� ���� ��� ������, ����� false
     */
    boolean deleteFile(String name, String type);

    /**
     * @author ������� ��������������
     *
     * ����� ���������� �������������� �������
     */
    void defragmentation();

    String getSystemInfo();
}