package mosecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

//import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@Configuration // для файла
public class ApplicationLauncher extends SpringBootServletInitializer {


    // для файла
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize("10MB");
//        factory.setMaxRequestSize("10MB");
        factory.setMaxFileSize(DataSize.of(100, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(100, DataUnit.MEGABYTES));

        return factory.createMultipartConfig();
    }


    public static void main(String[] args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }
}
