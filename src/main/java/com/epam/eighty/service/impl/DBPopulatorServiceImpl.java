package com.epam.eighty.service.impl;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.epam.eighty.service.DBPopulatorService;

@Service
public class DBPopulatorServiceImpl implements DBPopulatorService {

    @Autowired
    private GraphDatabaseService graphDatabaseService;
    
    @Value("classpath:scripts/data.cypher")
    private Resource createScriptResource;
    
    private static final String DELETE_SCRIPT = "START n=node(*) OPTIONAL MATCH (n)-[r]-() delete n,r;";

    @Override
    public void populate() throws IOException {
        String cypherScript = FileUtils.readFileToString(createScriptResource.getFile());

        ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);
        engine.execute(cypherScript);
    }

    @Override
    public void clean() throws IOException {
        ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);
        engine.execute(DELETE_SCRIPT);
    }

}
