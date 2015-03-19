package com.epam.eighty.web.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codahale.metrics.annotation.Timed;
import com.epam.eighty.service.DBPopulatorService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api(value = "/db", description = "All operations for database population")
@Controller
@RequestMapping("/db")
@Profile({"dev", "test"})
public class DBPopulatorController {

    @Autowired
    private DBPopulatorService dbPopulatorService;

    @ApiOperation(value = "Reload database", notes = "Reload database", httpMethod = "GET")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Invalid ID") })
    @Timed
    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    public void reloadDataBase(final HttpServletResponse response) throws IOException {
        dbPopulatorService.clean();
        dbPopulatorService.populate();
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
