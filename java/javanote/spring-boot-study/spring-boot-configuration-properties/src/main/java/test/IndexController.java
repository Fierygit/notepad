package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {

    @Autowired
    PersonProperties psersion;


    @GetMapping("/get")
    @ResponseBody
    public PersonProperties getPersion() {
        return psersion;
    }

}
