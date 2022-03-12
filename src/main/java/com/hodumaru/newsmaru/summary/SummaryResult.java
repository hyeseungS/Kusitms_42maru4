package com.hodumaru.newsmaru.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResult {
    private Object status;
    private String[] summarizes;
}