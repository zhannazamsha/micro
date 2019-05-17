package micro;


import micro.services.CountryCodesHTMLParsingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
public class Application {


    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

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
