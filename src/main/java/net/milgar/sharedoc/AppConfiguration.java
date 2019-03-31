package net.milgar.sharedoc;

import net.milgar.sharedoc.domain.service.TermClassService;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Autowired
    private TermClassService termClassService;

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }


    @Bean(name = "termClassService")
    public TermClassService termClassService() {
        return termClassService;
    }
}
