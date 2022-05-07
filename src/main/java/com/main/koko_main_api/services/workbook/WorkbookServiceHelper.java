package com.main.koko_main_api.services.workbook;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.domains.Workbook;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkbookServiceHelper {
    public List<Long> urls_to_ids(List<URI> patterns) {
        return patterns.stream().map(p -> convertURItoID(p)).collect(Collectors.toList());
    }

    public List<Pattern> patterns(Workbook workbook) {
        return workbook.getPatterns().stream().map(w -> w.getPattern()).collect(Collectors.toList());
    }

    private Long convertURItoID(URI uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
