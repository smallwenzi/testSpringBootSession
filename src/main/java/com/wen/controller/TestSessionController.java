package com.wen.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestSessionController {

    @RequestMapping(value = "/testSession")
    @ResponseBody
    public void doRefreshRam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.print("before: set session:test=123");
        request.getSession().setAttribute("test", "123");
        printWriter.print("get session" + request.getSession().getAttribute("test") + ":" + request.getLocalAddr()
                + ":" + request.getSession().getId());
    }

    @RequestMapping(value = "/getSession")
    @ResponseBody
    public void getSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.print("get session" + request.getSession().getAttribute("test") + ":" + request.getLocalAddr()
                + ":" + request.getSession().getId() + ":" + request.getRequestURI());

    }
}
