package com.main.koko_main_api.services;

import com.main.koko_main_api.services.workbook.WorkbookServiceHelper;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkbookServiceHelperTest {
    private WorkbookServiceHelper helper = new WorkbookServiceHelper();

    @Test
    void test() {
        List<URI> urls = new ArrayList<>();
        urls.add(URI.create("/patterns/1")); urls.add(URI.create("/patterns/2"));
        List<Long> patterns = helper.call(urls);

        assertThat(patterns.get(0).longValue()).isEqualTo(1L);
        assertThat(patterns.get(1).longValue()).isEqualTo(2L);
    }
}
