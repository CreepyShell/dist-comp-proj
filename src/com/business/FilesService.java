package com.business;

import com.data.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class FilesService {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public boolean writeInFile(Message message) throws IOException {
        FileWriter writer = new FileWriter("example.txt");
        String date = formatter.format(message.getDate());
        writer.write(message.getId() + "," + message.getText() + "," + date + "\n");
        writer.close();
        return true;

    }

    public List<Message> readMessages() throws FileNotFoundException, ParseException {
        List<Message> messages = new ArrayList<>();
        File file = new File("example.txt");
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            messages.add(new Message(line[0], line[1], formatter.parse(line[2])));
        }
        reader.close();
        return messages;
    }
}
