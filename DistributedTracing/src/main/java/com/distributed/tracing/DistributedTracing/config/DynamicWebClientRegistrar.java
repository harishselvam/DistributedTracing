package com.distributed.tracing.DistributedTracing.config;

import com.distributed.tracing.DistributedTracing.util.WebClientLoggingFilter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;

@Configuration
public class DynamicWebClientRegistrar implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        Binder binder = Binder.get(environment);
        Map<String, Object> services = binder.bind("client", Map.class).orElse(Map.of());

        services.forEach((name, config) -> {
            String baseUrl = ((Map<String, String>) config).get("baseurl");
            BeanDefinition beanDef = BeanDefinitionBuilder
                    .genericBeanDefinition(WebClient.class,
                            () -> WebClient
                                    .builder()
                                    .filter(WebClientLoggingFilter.logRequest())
                                    .filter(WebClientLoggingFilter.logResponseBody())
                                    .baseUrl(baseUrl)
                                    .build())
                    .getBeanDefinition();
            registry.registerBeanDefinition(name + "Client", beanDef);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {}
}
