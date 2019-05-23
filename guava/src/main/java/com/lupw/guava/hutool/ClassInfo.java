package com.lupw.guava.hutool;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author v_pwlu 2019/5/23
 */
@Data
@Builder
public class ClassInfo implements Serializable {
    private String name;
    private int score;
}
