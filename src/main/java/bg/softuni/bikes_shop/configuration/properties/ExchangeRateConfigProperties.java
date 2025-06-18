package bg.softuni.bikes_shop.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "frankfurter")
public class ExchangeRateConfigProperties {
    private String schema;
    private String host;
    private String path;
    private List<String> symbols;
    private boolean enabled;

    public String getSchema() {
        return schema;
    }

    public ExchangeRateConfigProperties setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getHost() {
        return host;
    }

    public ExchangeRateConfigProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ExchangeRateConfigProperties setPath(String path) {
        this.path = path;
        return this;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public ExchangeRateConfigProperties setSymbols(List<String> symbols) {
        this.symbols = symbols;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ExchangeRateConfigProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String toString() {
        return "ExchangeRateConfig{" +
                "schema='" + schema + '\'' +
                ", host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", symbols=" + symbols +
                ", enabled=" + enabled +
                '}';
    }
}