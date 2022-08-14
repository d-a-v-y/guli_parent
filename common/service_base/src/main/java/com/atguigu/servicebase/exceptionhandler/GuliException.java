package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Davy
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{

    private Integer code;//状态码
    private String msg;//异常信息
}
