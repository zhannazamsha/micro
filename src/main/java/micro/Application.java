package micro;


import micro.services.CountryCodesHTMLParsingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
    public class Application {
        public static void main(String[] args) {
            ApplicationContext context = SpringApplication.run(Application.class, args);
            CountryCodesHTMLParsingService startService = context.getBean(CountryCodesHTMLParsingService.class);
            try {
                startService.collectCountryCodes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
