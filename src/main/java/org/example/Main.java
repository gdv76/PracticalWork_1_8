package org.example;

import org.example.config.DefaultAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
//        Yaml yaml = new Yaml();
//        Main t = new Main();
//        InputStream input = t.getClass().getClassLoader().getResourceAsStream("application.yml");
//        Map<String, Object> map = yaml.load(input);
//        Map<String, Object> mapSpring = (Map<String, Object>)map.get("spring");
//        Map<String, Object> mapProfiles = (Map<String, Object>)mapSpring.get("profiles");
//        String name = (String) mapProfiles.get("active");
//        System.out.println("Свойство = " + name);

        ApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class);
        try {
            Contacts contacts = context.getBean(Contacts.class);
            Scanner sc = new Scanner(System.in);
            Integer commandCode = 0;

            HelpInfo helpInfo = context.getBean(HelpInfo.class);
            do {
                helpInfo.show();

                System.out.println("Выберете команду:");
                try {
                    commandCode = sc.nextInt();
                } catch (Exception ex) {
                    commandCode = -1;
                    sc.skip(".*\n");
                }

                switch (commandCode) {
                    case 0: {
                        System.out.println("Работа с приложением завершена");
                        break;
                    }
                    case 1: {
                        System.out.println("\nСписок контактов\n");
                        contacts.show();
                        break;
                    }
                    case 2: {
                        System.out.println("Добавление нового контакта");
                        Scanner contactInput = new Scanner(System.in);
                        System.out.println("ФИО:");
                        String fio = contactInput.nextLine();
                        System.out.println("Телефон:");
                        String tel = contactInput.nextLine();
                        System.out.println("Email:");
                        String email = contactInput.nextLine();

                        if (!contacts.contactIsPresent(email)) {
                            contacts.add(new Contact(fio,tel,email));
                            System.out.println("Контакт успешно добавлен");
                        } else {
                            System.out.println("Контакт с указанной почтой уже присутствует в списке");
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Удаление контакта");
                        System.out.println("Укажите почту удаляемого контакта:");
                        if (contacts.delete((new Scanner(System.in)).nextLine()) == null) {
                            System.out.println("Удаление из списка не произведено. Проверьте корректность задания email контакта");
                        } else {
                            System.out.println("Контакт успешно удален");
                        };
                        break;
                    }
                    case 4: {
                        System.out.println("Сохранение контактов в файл");
                        contacts.saveContactsToFile("");
                        break;
                    }
                    default: {
                        System.out.println("Введена не допустимая команда\n");
                    }
                }

            } while (commandCode != 0);
        } catch (Exception ex) {
            System.out.println("Ошибка инициализации приложения. " + ex.getMessage());
        }

    }
}