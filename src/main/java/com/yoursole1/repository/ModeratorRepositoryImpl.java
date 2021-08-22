package com.yoursole1.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Getter
public class ModeratorRepositoryImpl implements ModeratorRepository {

    private static ModeratorRepositoryImpl instance;
    private final Path databasePath;

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

    @Override
    public Logger getLogger() {
        return log;
    }
}
