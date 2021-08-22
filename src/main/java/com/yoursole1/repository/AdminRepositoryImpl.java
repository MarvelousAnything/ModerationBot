package com.yoursole1.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Getter
public class AdminRepositoryImpl implements AdminRepository {
    private static AdminRepositoryImpl instance;
    private final Path databasePath;

    public AdminRepositoryImpl(Path databasePath) {
        this.databasePath = databasePath;
    }

    public AdminRepositoryImpl() {
        this(Paths.get("", "admins.txt").toAbsolutePath().normalize());
    }

    public static synchronized AdminRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new AdminRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
