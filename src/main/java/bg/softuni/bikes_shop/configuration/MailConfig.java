package bg.softuni.bikes_shop.configuration;

import bg.softuni.bikes_shop.configuration.properties.MailConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    private final MailConfigProperties mailConfigProperties;

    public MailConfig(MailConfigProperties mailConfigProperties) {
        this.mailConfigProperties = mailConfigProperties;
    }

    @Bean
    public JavaMailSender javaMailSender(){

        JavaMailSenderImpl javaMailSender= new JavaMailSenderImpl();

        javaMailSender.setHost(mailConfigProperties.getHost());
        javaMailSender.setPort(mailConfigProperties.getPort());
        javaMailSender.setUsername(mailConfigProperties.getUsername());
        javaMailSender.setPassword(mailConfigProperties.getPassword());
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setJavaMailProperties(emailProperties());

        return  javaMailSender;
    }
    private Properties emailProperties(){
         Properties properties= new Properties();

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        return properties;
    }
}

