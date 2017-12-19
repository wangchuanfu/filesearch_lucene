package cn.filesearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Yao Pan on 17/12/19.
 */

@Controller
public class IndexController {

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    public String index() {

        return "index";
    }

}
