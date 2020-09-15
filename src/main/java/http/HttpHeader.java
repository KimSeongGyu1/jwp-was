package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.RequestHeaderCreateFailException;
import utils.IOUtils;

public class HttpHeader {
    private static final String HEADER_SEPERATOR = ": ";
    private static final int SPLIT_SIZE = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String DEFAUlT_VALUE = "";
    private static final String JOIN_SEPERATOR = ",";

    private Map<String, String> headers;

    private HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeader empty() {
        return new HttpHeader(new HashMap<>());
    }

    public static HttpHeader from(BufferedReader bufferedReader) {
        Map<String, String> headers = new HashMap<>();

        try {
            extractHeaders(bufferedReader, headers);
        } catch (IOException e) {
            throw new RequestHeaderCreateFailException();
        }

        return new HttpHeader(headers);
    }

    private static void extractHeaders(BufferedReader bufferedReader, Map<String, String> headers) throws IOException {
        List<String> lines = IOUtils.readDataUntilEmpty(bufferedReader);
        for (String line : lines) {
            String[] keyValues = line.split(HEADER_SEPERATOR, SPLIT_SIZE);
            headers.put(keyValues[KEY_INDEX], keyValues[VALUE_INDEX]);
        }
    }

    public void addHeader(String key, String value) {
        if (headers.containsKey(key)) {
            String old = headers.get(key);
            headers.replace(key, old + JOIN_SEPERATOR + value);
        } else {
            headers.put(key, value);
        }
    }

    public String findOrEmpty(String input) {
        return headers.getOrDefault(input, DEFAUlT_VALUE);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}