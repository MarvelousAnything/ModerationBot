package com.yoursole1.service;

import com.yoursole1.repository.AdminRepositoryImpl;

public final class AdminService extends AbstractStaticFileMemberService {
    private static AdminService instance;

    private AdminService() {
        super(AdminRepositoryImpl.getInstance());
    }

    public static synchronized AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }

    static {
        getInstance();
    }
}
