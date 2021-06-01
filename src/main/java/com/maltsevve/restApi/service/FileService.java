package com.maltsevve.restApi.service;

import com.maltsevve.restApi.model.File;
import com.maltsevve.restApi.repository.FileRepository;
import com.maltsevve.restApi.repository.JavaIOFileRepositoryImpl;

import java.util.List;

public class FileService {
    private final FileRepository fileRepository = new JavaIOFileRepositoryImpl();

    public File save(File file) {
        return fileRepository.save(file);
    }

    public File update(File file) {
        return fileRepository.update(file);
    }

    public File getById(Long aLong) {
        return fileRepository.getById(aLong);
    }

    public List<File> getAll() {
        return fileRepository.getAll();
    }

    public void deleteById(Long aLong) {
        fileRepository.deleteById(aLong);
    }
}
