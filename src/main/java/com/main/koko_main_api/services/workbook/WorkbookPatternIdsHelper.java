package com.main.koko_main_api.services.workbook;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkbookPatternIdsHelper {

    public List<Long> call(List<URI> patterns) {
        return patterns.stream().map(p -> convertURItoID(p))
                .collect(Collectors.toList());
    }

    private Long convertURItoID(URI uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
