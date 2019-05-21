package micro;


import micro.services.CountryCodesHTMLParsingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;

@SpringBootApplication
//@EnableEurekaClient
public class Application {




    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        CountryCodesHTMLParsingService startService = context.getBean(CountryCodesHTMLParsingService.class);
        Environment env = context.getBean(Environment.class);

        try {
            startService.collectCountryCodes(env.getProperty("spring.application.countryCodesLink")
                    , env.getProperty("spring.application.countryCodesStartText"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
