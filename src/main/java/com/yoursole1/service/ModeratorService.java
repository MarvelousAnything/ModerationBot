package com.yoursole1.service;

import com.yoursole1.repository.ModeratorRepositoryImpl;

public final class ModeratorService extends AbstractStaticFileMemberService {
    private static ModeratorService instance;

    private ModeratorService() {
        super(ModeratorRepositoryImpl.getInstance());
    }

    public static synchronized ModeratorService getInstance() {
        if (instance == null) {
            instance = new ModeratorService();
        }
        return instance;
    }

    static {
        getInstance();
    }
}
