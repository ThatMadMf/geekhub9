package org.geekhub.crypto.history;

import java.util.LinkedList;

interface HistoryPrinter {
    void print(LinkedList<HistoryRecord> history);
}
