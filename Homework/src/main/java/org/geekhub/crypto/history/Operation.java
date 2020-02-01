package org.geekhub.crypto.history;

public enum Operation {
    ENCODE,
    DECODE,
    SHOW_HISTORY,
    CLEAR_HISTORY,
    ANALYTICS;

    @Override
    public String toString() {
        switch (this) {
            case ENCODE:
                return "Encode";
            case DECODE:
                return "Decode";
            case SHOW_HISTORY:
                return "Show history";
            case CLEAR_HISTORY:
                return "Clear history";
            case ANALYTICS:
                return "Show analytics";
            default:
                throw new IllegalArgumentException();
        }
    }
}
