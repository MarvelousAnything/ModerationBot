package com.yoursole1.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface StaticFileRepository extends Repository<String, Integer> {

    Path getDatabasePath();

    default Stream<String> openDatabaseStream() {
        Stream<String> input = Stream.<String>builder().build();
        try {
            input = Files.lines(getDatabasePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    @Override
    default void save(String string) {
        string = string + System.lineSeparator();
        try {
            Files.write(getDatabasePath(), string.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    default void save(List<String> strings) {
        try {
            Files.write(getDatabasePath(), strings, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    default void delete(String string) {
        List<String> filtered = openDatabaseStream()
                .filter(item -> !item.equals(string))
                .collect(Collectors.toList());
        try {
            Files.write(getDatabasePath(), filtered, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    default void delete(Integer id) {
        String tag = openDatabaseStream().collect(Collectors.toList()).get(id);
        delete(tag);
    }

    @Override
    default void deleteAll() {
        try {
            Files.deleteIfExists(getDatabasePath());
            Files.createFile(getDatabasePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    default String get(Integer id) {
        return openDatabaseStream().collect(Collectors.toList()).get(id);
    }

    @Override
    default List<String> getAll() {
        return openDatabaseStream().collect(Collectors.toList());
    }
}
