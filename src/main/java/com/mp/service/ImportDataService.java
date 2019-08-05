package com.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class ImportDataService implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImportDataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void run(String... args) throws Exception {

        String path ="C:\\Users\\RYX\\Desktop\\连连撞库-2019-08-05\\";
        String succ = path + "success_20190805.txt";
        String fail = path + "fail_20190805.txt";
        System.out.println("开始执行脚本-" + fail);
        try (Stream<String> stream = Files.lines(Paths.get(fail))) {
            stream.parallel().forEachOrdered(it -> {
                try {
                    jdbcTemplate.execute(it);
                } catch (Exception e) {
                    System.out.println(it);
                    e.printStackTrace();
                }
            });
        }
        System.out.println("脚本执行完成-" +fail);
    }
}
