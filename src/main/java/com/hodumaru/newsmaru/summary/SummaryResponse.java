package com.hodumaru.newsmaru.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {

    private Object summary;
    private Long articleId;
}
