package com.thread.excutor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author v_pwlu 2018/10/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataNode {
    private String name;
    private int soc;
}
