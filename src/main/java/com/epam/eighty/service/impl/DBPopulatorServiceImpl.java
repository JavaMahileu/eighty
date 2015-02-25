package com.epam.eighty.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.eighty.service.DBPopulatorService;

@Service
public class DBPopulatorServiceImpl implements DBPopulatorService {

    @Autowired
    private GraphDatabaseService graphDatabaseService;

    @Override
    public void populate() throws IOException {
        String cypherScript = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource("scripts/data.cypher").getFile()));

        ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);
        engine.execute(cypherScript);
    }

}
