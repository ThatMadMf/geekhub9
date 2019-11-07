package org.geekhub.crypto.history;

import org.geekhub.crypto.analytics.CodecUsecase;

public enum Operation {
    ENCODE,
    DECODE,
    SHOW_HISTORY,
    ANALYTICS;

    public static Operation usecaseToOperation(CodecUsecase usecase) {
        switch (usecase) {
            case ENCODING:
                return ENCODE;
            case DECODING:
                return DECODE;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ENCODE:
                return "Encode";
            case DECODE:
                return "Decode";
            case SHOW_HISTORY:
                return "Show history";
            case ANALYTICS:
                return "Show analytics";
            default:
                throw new IllegalArgumentException();
        }
    }
}
