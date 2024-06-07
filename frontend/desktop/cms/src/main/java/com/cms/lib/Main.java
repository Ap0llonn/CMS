package com.cms.lib;

import com.cms.lib.error.ApiRequestException;
import com.cms.lib.error.JsonFormattingException;
import com.cms.lib.error.JsonParsingException;
import com.cms.lib.request.Get;

public class Main {

    public static void main(String[] args) throws JsonParsingException, JsonFormattingException, ApiRequestException {
        NetConex netConex = new NetConex("https://dummyjson.com");

        Get getRequest = new Get(netConex);
        getRequest.executeAsync("/users", null)
                .thenApply(res -> {
                    try {
                        return getRequest.formatResponse(res);
                    } catch (JsonParsingException | JsonFormattingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenAccept(formatResponse -> {
                    if (formatResponse != null) {
                        System.out.println(formatResponse);
                    } else {
                        System.out.println("Formatting response failed.");
                    }
                }).join();
    }
}
