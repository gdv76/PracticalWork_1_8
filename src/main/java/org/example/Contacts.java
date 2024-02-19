package org.example;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Contacts {
    private String saveFileName="contacts.txt";

    private Map<String, Contact> c;

    public Contacts() {
        c = new HashMap<>();
    }

    public Contacts(String pathToFile) throws IOException {
        c = loadContactsFromFile(pathToFile);
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public Boolean contactIsPresent(String email) {
        return c.containsKey(email);
    }

    public void add(Contact contact) {
        c.put(contact.getEmail(), contact);
    }

    public Contact delete(String email) {
        return c.remove(email);
    }

    public void show() {
        int i = 0;
        for (Contact contact : c.values()) {
            System.out.println(++i + ") " + contact.getFullName() + " тел: " + contact.getPhoneNumber() + " почта: " + contact.getEmail());
        }
    }

    public Map<String, Contact> loadContactsFromFile(String pathToFile) throws IOException {
        Map<String, Contact> contactMap = new HashMap<>();
        BufferedReader reader = null;
        try {
            String row;

            reader = new BufferedReader(new FileReader(pathToFile));
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(";");
                if (data.length != 3) {
                    throw new IOException("Invalid file format");
                }
                contactMap.put(data[2], new Contact(data[0], data[1], data[2]));
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                reader.close();
// пишем обработку исключения при закрытии потока чтения
            } catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return contactMap;
    }

    public void saveContactsToFile(String pathToFile) throws IOException {
        if (!pathToFile.isEmpty()) {
            saveFileName = pathToFile;
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(saveFileName);
            for (Contact contact : c.values()) {
                writer.append(String.join(";",contact.getFullName(),contact.getPhoneNumber(),contact.getEmail()));
                writer.append("\n");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                writer.flush();
                writer.close();
// пишем обработку исключения при закрытии потока чтения
            } catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

