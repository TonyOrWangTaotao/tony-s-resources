package com.servlet;/**
 * Created by Tony on 2018/3/20.
 */

import com.tony.util.WebContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet线程安全的测试
 *
 * @author Tony
 * @create 2018-03-20 上午11:37
 **/
public class ServletThread implements Runnable{
    //无参数构造器
    public ServletThread() {
    }

    public static void main(String[] args) {
        for (int i = 0;i<100;i++) {
            new Thread(new ServletThread()).start();
        }
    }
    @Override
    public void run() {

        HttpServletRequest request=null;
        HttpServletResponse response=null;
        try {
            WebContainer.getServlet().doGet(request, response);
        }
        catch (IOException ex) {
        }
        catch (ServletException ex) {
        }
    }
}
