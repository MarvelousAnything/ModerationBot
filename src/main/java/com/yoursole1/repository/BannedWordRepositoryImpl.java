package com.yoursole1.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Getter
public class BannedWordRepositoryImpl implements BannedWordRepository {
    private static BannedWordRepositoryImpl instance;
    private final Path databasePath;

    public BannedWordRepositoryImpl(Path databasePath) {
        this.databasePath = databasePath;
    }

    public BannedWordRepositoryImpl() {
        this(Paths.get("", "bannedwords.txt").toAbsolutePath().normalize());
    }

    public static synchronized BannedWordRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new BannedWordRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
