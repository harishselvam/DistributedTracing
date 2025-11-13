package com.distributed.tracing.DistributedTracing.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "client")
public class ServicesProperties {
    private Map<String, ServiceConfig> configs;

    public Map<String, ServiceConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, ServiceConfig> configs) {
        this.configs = configs;
    }

    public static class ServiceConfig {
        private String baseurl;
        public String getBaseurl() { return baseurl; }
        public void setBaseurl(String baseurl) { this.baseurl = baseurl; }
    }
}
