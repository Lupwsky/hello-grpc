package com.cloud.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author v_pwlu 2019/5/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoParams {
    private String username;
}
