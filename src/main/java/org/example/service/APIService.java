package org.example.service;

import org.example.model.APIParam;

public class APIService {
    private static final APIService instance = new APIService();

    public static APIService getInstance() {
        return instance;
    }

    private APIService() {}

    public String callAPI(APIParam apiParam) {
        return "%s %s".formatted(apiParam.prompt(), apiParam.model());
    }
}
