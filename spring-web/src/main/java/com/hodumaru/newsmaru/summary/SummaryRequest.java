package com.hodumaru.newsmaru.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryRequest {

    private Content[] net_input;
    private boolean extractive;
}

