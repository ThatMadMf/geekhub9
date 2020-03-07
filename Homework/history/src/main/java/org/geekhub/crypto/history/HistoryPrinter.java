package org.geekhub.crypto.history;

import java.util.List;

public interface HistoryPrinter {
    void print(List<HistoryRecord> history);
}
