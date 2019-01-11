package com.thread.excutor.beancopy;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author v_pwlu 2018/12/13
 */
@Data
@Builder
public class DetailInfo1 implements Serializable {
    private int age;
    private String sex;
}
