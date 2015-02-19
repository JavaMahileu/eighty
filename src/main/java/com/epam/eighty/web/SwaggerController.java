package com.epam.eighty.web;

import com.knappsack.swagger4springweb.controller.ApiDocumentationController;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/documentation")
public class SwaggerController extends ApiDocumentationController {

    public SwaggerController() {
        setBaseControllerPackage("com.epam.eighty.web.api");

        setBaseModelPackage("com.epam.eighty.domain");

        setApiVersion("v1.0");

        ApiInfo apiInfo = new ApiInfo("eighty app", "", "", "", "", "");
        setApiInfo(apiInfo);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String documentation() {
        return "/docu";
    }
}
