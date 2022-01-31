package com.main.koko_main_api.services;

import java.net.URI;

public class URIToID {
    protected Long convertURItoID(URI uri) {
        String[] datas = uri.toString().split("/");
        return Long.parseLong(datas[datas.length - 1]);
    }
}
