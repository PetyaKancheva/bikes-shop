package bg.softuni.bikes_shop.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "comments-server")
public class CommentsServerConfigProperties {
    private String schema;
    private String host;
    private Integer port;
    private String path;
    private boolean enabled;

    public String getSchema() {
        return schema;
    }

    public CommentsServerConfigProperties setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getHost() {
        return host;
    }

    public CommentsServerConfigProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public CommentsServerConfigProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public CommentsServerConfigProperties setPath(String path) {
        this.path = path;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public CommentsServerConfigProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}