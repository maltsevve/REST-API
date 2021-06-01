package com.maltsevve.restApi.repository;

import com.maltsevve.restApi.model.File;

import java.util.List;

public interface FileRepository extends GenericRepository<File, Long>{
    @Override
    File save(File file);

    @Override
    File update(File file);

    @Override
    File getById(Long aLong);

    @Override
    List<File> getAll();

    @Override
    void deleteById(Long aLong);
}
