package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class ApplicationQuickStart {



    public static void main(String[] args) {
        SpringApplication.run(ApplicationQuickStart.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "welcome Application QuickStart ";
    }
}
