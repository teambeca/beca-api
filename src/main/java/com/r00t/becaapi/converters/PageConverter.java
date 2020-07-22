package com.r00t.becaapi.converters;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageConverter {

    public Map<String, Object> convertPageToMap(Page page) {
        return Map.of(
                "content", page.getContent(),
                "totalPages", page.getTotalPages(),
                "totalElements", page.getTotalElements()
        );
    }
}
