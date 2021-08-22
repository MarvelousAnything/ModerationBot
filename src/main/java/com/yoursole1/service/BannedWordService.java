package com.yoursole1.service;

import com.yoursole1.repository.BannedWordRepositoryImpl;

import java.util.List;

public final class BannedWordService extends AbstractStaticFileService<String, Integer> {
    private static BannedWordService instance;

    private BannedWordService() {
        super(BannedWordRepositoryImpl.getInstance());
    }

    public static synchronized BannedWordService getInstance() {
        if (instance == null) {
            instance = new BannedWordService();
        }
        return instance;
    }

    static {
        getInstance();
    }

    @Override
    public void add(String word) {
        getRepository().save(word);
    }

    @Override
    public void add(List<String> words) {
        getRepository().save(words);
    }

    @Override
    public void remove(String word) {
        getRepository().delete(word);
    }

    @Override
    public String find(Integer id) {
        return getRepository().get(id);
    }

    @Override
    public List<String> findAll() {
        return getRepository().getAll();
    }
}
