package org.example.config;

import org.example.Contacts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.io.IOException;

@Configuration
@PropertySource("classpath:application.yml")
@Profile("init")
public class InitAppConfig {
    @Value("${init-path}")
    private String pathToInitFile;

    @Value("${save-path}")
    private String exportFileName;

    @Bean
    public Contacts appContacts() {
        Contacts contacts = null;
        try {
            contacts = new Contacts(pathToInitFile);
            contacts.setSaveFileName(exportFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }
}
