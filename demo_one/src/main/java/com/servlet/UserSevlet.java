package com.servlet;/**
 * Created by Tony on 2018/3/20.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 玩玩servlet
 *
 * @author Tony
 * @create 2018-03-20 上午10:44
 **/
public class UserSevlet extends HttpServlet{

    private String unsafe;
    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        unsafe=Thread.currentThread().getName();
        System.out.println(unsafe);
        System.out.println("++++++++++++++测试servlet+++++++++++++++++++GET");
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("++++++++++++++测试servlet+++++++++++++++++++POST");
    }
}
