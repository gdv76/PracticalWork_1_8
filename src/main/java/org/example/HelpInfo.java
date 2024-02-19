package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
@PropertySource("classpath:application.yml")
public class HelpInfo {
    @Value("${help-path}")
    private String helpFileName;

    public void show() throws IOException {
        BufferedReader reader = null;
        try {
            String row;
            reader = new BufferedReader(new FileReader(helpFileName));
            while ((row = reader.readLine()) != null) System.out.println(row);
        } catch (IOException ex) {
            System.out.println("Ошибка получения данных из файла " + helpFileName);
            throw ex;
        } finally {
            try {
                reader.close();
// пишем обработку исключения при закрытии потока чтения
            } catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
