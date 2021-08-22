package com.yoursole1.service;

import com.yoursole1.repository.StaticFileRepository;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class AbstractStaticFileService<T, ID extends Serializable> implements Service<T, ID> {
    private final StaticFileRepository repository;

    AbstractStaticFileService(StaticFileRepository repository) {
        this.repository = repository;
    }
}
