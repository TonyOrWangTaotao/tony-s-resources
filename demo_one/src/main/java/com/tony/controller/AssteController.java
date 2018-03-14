package com.tony.controller;/**
 * Created by Tony on 2018/3/14.
 */

import com.tony.domain.Assets;
import com.tony.service.AssetsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Tony
 * @create 2018-03-14 下午4:28
 **/
@Controller
@RequestMapping("/assts")
public class AssteController {
    @Resource
    private AssetsService service;

    @RequestMapping("/showUser")
    public String toIndex(HttpServletRequest request, Model model){
        List<Assets> assets = this.service.queryAssetsList();
        model.addAttribute("assets", assets);
        return "assets";
    }

}
