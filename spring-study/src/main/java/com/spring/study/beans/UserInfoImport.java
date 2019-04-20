package com.spring.study.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author v_pwlu 2018/11/8
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoImport {
    private String name;
    private String email;
}
