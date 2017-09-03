package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    public ViewResolver thymeleafViewResolver() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setTemplateMode("HTML5");
        fileTemplateResolver.setPrefix("./src/main/resources/templates/");
        fileTemplateResolver.setSuffix(".html");
        fileTemplateResolver.setCacheable(false);
        templateEngine.setTemplateResolver(fileTemplateResolver);
        final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCache(false);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setRedirectHttp10Compatible(false);
        resolver.setOrder(1);
        return resolver;
    }
}
