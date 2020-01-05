package org.geekhub.crypto.history;

import org.geekhub.crypto.exception.EmptyHistoryException;
import org.geekhub.crypto.exception.FileProcessingFailedException;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;

public class HistoryManager {
    private final Path history;

    public HistoryManager() {
        history = getHistoryFile("history.ser");
    }


    public void saveHistory(List<HistoryRecord> records) {
        try (OutputStream fileOutputStream = Files.newOutputStream(history);
             ObjectOutputStream stream = new ObjectOutputStream(fileOutputStream)
        ) {
            for (HistoryRecord record : records) {
                stream.writeObject(record);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public LinkedList<HistoryRecord> readHistory() {
        LinkedList<HistoryRecord> records = new LinkedList<>();
        try (InputStream fileInputStream = Files.newInputStream(history);
             BufferedInputStream bis = new BufferedInputStream(fileInputStream);
             ObjectInputStream in = new ObjectInputStream(bis)
        ) {
            while (true) {
                Object record = in.readObject();
                if (record instanceof HistoryRecord) {
                    records.add((HistoryRecord) record);
                }
            }
        } catch (NoSuchFileException e) {
            throw new EmptyHistoryException("Cannot find the file to read from");
        } catch (ClassNotFoundException e) {
            throw new FileProcessingFailedException("File contains invalid data");
        } catch (IOException e) {
            return records;
        }
    }

    private Path getHistoryFile(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL historyFile = classLoader.getResource(path);
        return Paths.get(historyFile.getPath());

    }
}
