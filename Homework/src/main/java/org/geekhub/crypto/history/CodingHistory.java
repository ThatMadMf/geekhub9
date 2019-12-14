package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;
import org.geekhub.crypto.ui.LogManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodingHistory {
    private final LinkedList<HistoryRecord> historyRecords;

    public List<HistoryRecord> getHistoryRecords() {
        return historyRecords;
    }

    public List<HistoryRecord> getHistoryRecords(CodecUsecase usecase) {
        List<HistoryRecord> result = new ArrayList<>();
        for (HistoryRecord record : historyRecords) {
            if (record.getOperation() == Operation.usecaseToOperation(usecase)) {
                result.add(record);
            }
        }
        return result;
    }

    public CodingHistory() {
        LinkedList<HistoryRecord> temp = readHistory();
        historyRecords = temp.isEmpty() ? new LinkedList<>() : temp;
    }

    public void addToHistory(HistoryRecord record) {
        if (record == null) {
            throw new IllegalArgumentException();
        }
        historyRecords.add(record);
        saveHistory();
    }

    public void clearHistory() {
        historyRecords.clear();
        saveHistory();
    }

    public void removeLastRecord() {
        historyRecords.pollLast();
        saveHistory();
    }

    private void saveHistory() {
        Path path = Paths.get("Homework/history.ser").toAbsolutePath();
        try (OutputStream fileOutputStream = Files.newOutputStream(path);
             ObjectOutputStream stream = new ObjectOutputStream(fileOutputStream)
        ) {
            stream.writeObject(historyRecords);
        } catch (IOException e) {
            LogManager.error("Saving object failed");
        }
    }

    private LinkedList<HistoryRecord> readHistory() {
        Path path = Paths.get("Homework/history.ser").toAbsolutePath();
        try (InputStream fileInputStream = Files.newInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(fileInputStream);
             ObjectInputStream in = new ObjectInputStream(bis)
        ) {
            return (LinkedList<HistoryRecord>) in.readObject();
        } catch (NoSuchFileException e) {
            LogManager.warn("Cannot find the file to read from");
        } catch (NullPointerException | ClassNotFoundException e) {
            LogManager.warn("File contains invalid data");
        } catch (IOException e) {
            LogManager.error("Error while trying to read the file");
        }
        return new LinkedList<>();
    }

}
