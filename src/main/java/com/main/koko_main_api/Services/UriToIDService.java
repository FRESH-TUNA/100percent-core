package com.main.koko_main_api.Services;
import org.springframework.stereotype.Service;
import java.net.URI;

@Service
public class UriToIDService {
    public Long convert(URI uri) {
        String[] datas = uri.toString().split("/");
        return Long.parseLong(datas[datas.length - 1]);
    }
}
