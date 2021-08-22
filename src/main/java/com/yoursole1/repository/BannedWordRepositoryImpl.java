package com.yoursole1.repository;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

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
}
