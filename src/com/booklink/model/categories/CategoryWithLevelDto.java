package com.booklink.model.categories;

public record CategoryWithLevelDto(Long id,
                                   String name,
                                   Long priorId, // 부모 카테고리의 priorId == null
                                   Integer level) {
}
