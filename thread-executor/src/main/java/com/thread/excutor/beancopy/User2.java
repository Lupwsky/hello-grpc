package com.thread.excutor.beancopy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author v_pwlu 2018/12/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User2 implements Serializable {
    private String name2;
    private DetailInfo2 detailInfo2;
}
