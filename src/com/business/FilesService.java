package com.business;

import com.data.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class FilesService {
    private final String filename = "messages.txt";
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean writeInFile(Message message) throws IOException {
        File file = new File(filename);
        if (!file.exists() && !file.createNewFile()) {
            return false;
        }
        FileWriter writer = new FileWriter(file, true);
        String date = formatter.format(message.getDate());
        writer.write(message.getId() + "," + message.getText() + "," + date + "\n");
        writer.close();
        return true;
    }

    public ArrayList<Message> readMessages() throws FileNotFoundException, ParseException {
        ArrayList<Message> messages = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return messages;
        }
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            messages.add(new Message(line[0], line[1], formatter.parse(line[2])));
        }
        reader.close();
        return messages;
    }

    public Message getMessageById(String id) throws FileNotFoundException, ParseException {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            if (Objects.equals(line[0], id)) {
                return new Message(line[0], line[1], formatter.parse(line[2]));
            }
        }
        reader.close();
        return null;
    }
}
