package ps.springfinalproject.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// We can put all shared annotations above class, so they are not duplicated anymore above our methods:
@Controller
@ResponseBody // Tells Spring, that method must return plain string instead of template
public class HelloController {

//    @GetMapping("/hello")
//    @ResponseBody // Tells Spring, that method must return plain string instead of template
//    public String hello() {
//        return "Hello, this is just for test purposes.";
//    }

    // lives at /goodbye
    @GetMapping("/goodbye")
    public String goodbye() {
        return "Goodbye, this is just for test purposes.";
    }

    // Handles request of the form /hello?param=TestParameter

    // both annotations will not work together:
    //    @GetMapping("/hello")
    //    @PostMapping("/hello")

    // We have to use @RequestMapping:
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/hello")
    public String helloWithQueryParam(@RequestParam("param") String parameter) {
        return "Hello, this is param = " + parameter;
    }

    // Handles request of the form /hello/WhateverStringParam
    @GetMapping("/hello/{name}")
    public String helloWithPathParam(@PathVariable("name") String stringParam) {
        return "Hello, this is param = " + stringParam;
    }

    // lives at /form-get
    @GetMapping("/form-get")
    public String helloFormGet() {
        return "<html>" +
                "<body>" +
                "<form action='/hello'>" + // submit a request to /hello (if not specified default value -- method="GET")
                "<input type='text' name='param'/>" + // param submitted to helloWithQueryParam via /hello?param=formInputData
                "<input type='submit' value='Greet me!'/>" +
                "</form>" +
                "</body>" +
                "</html>";

    }

    // lives at /form-post
    @GetMapping("/form-post")
    public String helloFormPost() {
        return "<html>" +
                "<body>" +
                "<form action='/hello' method='post'>" + // submit a request to /hello
                "<input type='text' name='param'/>" + // param submitted to helloWithQueryParam via POST to /hello
                "<input type='submit' value='Greet me!'/>" +
                "</form>" +
                "</body>" +
                "</html>";

    }
}
