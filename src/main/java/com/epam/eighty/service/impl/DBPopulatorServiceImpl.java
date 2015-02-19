package com.epam.eighty.service.impl;

import com.epam.eighty.service.DBPopulatorService;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class DBPopulatorServiceImpl implements DBPopulatorService {

    @Autowired
    private GraphDatabaseService graphDatabaseService;

    @Override
    public void populate() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("scripts/data.cypher").getFile())));
        ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);

        StringBuilder builder = new StringBuilder();
        String st;
        while ((st = reader.readLine()) != null) {
            builder.append(st).append(" ");
        }

        engine.execute(builder.toString());
        reader.close();
    }

}
