package org.mbtest.javabank.fluent;

import org.json.simple.JSONObject;
import org.mbtest.javabank.http.responses.Is;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;

public class IsBuilder extends ResponseTypeBuilder {
    private int statusCode = 200;
    private String body = "";
    private String mode;
    private File bodyFile;
    private JSONObject jsonBody;
    private final HashMap<String, String> headers = newHashMap();

    public IsBuilder(ResponseBuilder responseBuilder) {
        super(responseBuilder);
    }

    public IsBuilder statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public IsBuilder header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public IsBuilder body(String body) {
        this.body = body;
        return this;
    }

    public IsBuilder body(File body) {
        this.bodyFile = body;
        return this;
    }

    public IsBuilder body(JSONObject body) {
        this.jsonBody = body;
        return this;
    }

    public IsBuilder mode(String mode){
        this.mode = mode;
        return this;
    }

    @Override
    protected Is build() {

        if (this.bodyFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(this.bodyFile.toPath());
                this.body = new String(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(jsonBody != null) {
            return new Is()
                    .withStatusCode(statusCode)
                    .withHeaders(headers)
                    .withBody(jsonBody)
                    .withMode(mode);
        }

        return new Is()
                .withStatusCode(statusCode)
                .withHeaders(headers)
                .withBody(body)
                .withMode(mode);
    }
}
