package com.yoursole1.repository;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class ModeratorRepositoryImpl implements ModeratorRepository {

    private final Path databasePath;
    private static ModeratorRepositoryImpl instance;

    public ModeratorRepositoryImpl(Path databasePath) {
        this.databasePath = databasePath;
    }

    private ModeratorRepositoryImpl() {
        this(Paths.get("", "moderators.txt").toAbsolutePath().normalize());
    }

    public static synchronized ModeratorRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ModeratorRepositoryImpl();
        }
        return instance;
    }
}
