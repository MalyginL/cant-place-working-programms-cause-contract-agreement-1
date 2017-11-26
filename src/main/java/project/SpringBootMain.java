package project;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootMain {


    final static Logger logger = Logger.getLogger(SpringBootMain.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
        logger.warn("--------app started--------");


    }

}
