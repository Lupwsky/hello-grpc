package com.thread.excutor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author v_pwlu 2018/11/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    String myAttribute;
    String name;
    String age;
    String sex;
}
