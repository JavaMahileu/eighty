package com.epam.eighty.service;

import java.io.IOException;

public interface DBPopulatorService {

    void populate() throws IOException;

    void clean() throws IOException;
}
