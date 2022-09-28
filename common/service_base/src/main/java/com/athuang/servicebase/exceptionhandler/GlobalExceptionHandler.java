package com.athuang.servicebase.exceptionhandler;





import com.athuang.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-04 17:57
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e)
    {
        e.printStackTrace();

        return R.error().message("执行了全局异常处理");
    }//

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e)
    {
        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
