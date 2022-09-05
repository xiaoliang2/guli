package com.athuang.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: guli_parent
 *
 * @description: 自定义异常类
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-05 14:56
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException{

//    异常状态码
    private Integer code;

//    异常信息
    private String msg;

}
