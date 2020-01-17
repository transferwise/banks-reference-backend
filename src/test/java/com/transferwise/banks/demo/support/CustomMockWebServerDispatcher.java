package com.transferwise.banks.demo.support;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.transferwise.banks.demo.ServerTest.response;

public class CustomMockWebServerDispatcher extends Dispatcher {

    private Map<String, String> pathToFileMap = new HashMap<>();

    @NotNull
    @Override
    public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) {
        String requestPath = recordedRequest.getPath();

        return pathToFileMap.keySet().stream()
                .filter(requestPath::contains)
                .findFirst()
                .map(path -> {
                    try {
                        return response(pathToFileMap.get(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new MockResponse().setResponseCode(404);
                }).orElse(new MockResponse().setResponseCode(404));
    }

    public CustomMockWebServerDispatcher withPathToFile(final String path, final String filename) {
        pathToFileMap.put(path, filename);
        return this;
    }

}
