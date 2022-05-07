package com.main.koko_main_api.deassemblers;

import java.net.URI;

public abstract class BaseDeassembler {
    protected Long convertURItoID(URI uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
