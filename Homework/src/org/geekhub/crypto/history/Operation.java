package org.geekhub.crypto.history;

public enum Operation {
    ENCODE,
    DECODE,
    CODEC_NAME,
    TEXT_TO_ENCODE,
    TEXT_TO_DECODE,
    SHOW_HISTORY;

    @Override
    public String toString() {
        switch (this) {
            case ENCODE:
                return "Encode";
            case DECODE:
                return "Decode";
            case CODEC_NAME:
                return "Codec name";
            case TEXT_TO_ENCODE:
                return "Text to encode";
            case TEXT_TO_DECODE:
                return "Text to decode";
            case SHOW_HISTORY:
                return "Show history";
            default:
                throw  new IllegalArgumentException();
        }
    }
}
