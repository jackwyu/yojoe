package com.heroes.gijoe.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class RequestDateConfig extends WebMvcConfigurationSupport {
	@Override
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = 
          new DefaultFormattingConversionService(false);
        conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());
        
        DateFormatterRegistrar registrar = new DateFormatterRegistrar();
//        registrar.setFormatter(new DateFormatter("[dd.MM.yyyy][MM-dd-yyyy]"));
        registrar.setFormatter(new DateFormatter("MM-dd-yyyy"));
        registrar.registerFormatters(conversionService);

        return conversionService;
    }
}
