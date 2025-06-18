package bg.softuni.bikes_shop.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="mail")
public class MailConfigProperties {
    private String username;
    private String password;
    private String host;
    private int port;

    private String email;

    public String getUsername() {
        return username;
    }

    public MailConfigProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MailConfigProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getHost() {
        return host;
    }

    public MailConfigProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public MailConfigProperties setPort(int port) {
        this.port = port;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MailConfigProperties setEmail(String email) {
        this.email = email;
        return this;
    }
}
