package mo.demo.controller;

import mo.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WindShadow
 * @verion 2020/8/16.
 */

@Controller
public class DemoController {

    @Autowired
    DemoService service;

    @RequestMapping("/demo/test")
    @ResponseBody
    public String sayDemo() {

        return service.getDemoString();
    }
}
