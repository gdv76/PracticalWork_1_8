package org.example.config;

import org.example.Contacts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application-empty.yml")
@Profile("empty")
public class EmptyAppConfig {
    @Value("${save-path}")
    private String exportFileName;

    @Bean
    public Contacts appContacts() {
        Contacts contacts = new Contacts();
        contacts.setSaveFileName(exportFileName);
        return contacts;
    }
}
