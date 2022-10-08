package com.athuang.commonutils;

import com.athuang.commonutils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ResponseUtil {

    public static void out(HttpServletResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
//        response.reset();//（清空缓冲区）
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {

            mapper.writeValue(new BufferedWriter(new OutputStreamWriter(response.getOutputStream())), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
