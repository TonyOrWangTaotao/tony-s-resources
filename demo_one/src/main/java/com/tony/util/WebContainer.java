package com.tony.util;/**
 * Created by Tony on 2018/3/20.
 */

import com.servlet.UserSevlet;

/**
 * 容器类
 *
 * @author Tony
 * @create 2018-03-20 上午11:49
 **/
public class WebContainer {
    private static UserSevlet us=new UserSevlet();
    public static UserSevlet getServlet(){
        return us;
    }
}
