package org.geekhub.crypto.history;

import java.util.List;

interface HistoryPrinter {
    void print(List<HistoryRecord> history);
}
