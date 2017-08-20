package com.example.stream.jx.generator;

import com.example.annotation.AppRegisterGenerator;
import com.example.stream.core.wechat.template.AppRegisterTemplate;


/**
 * Created by StReaM on 8/20/2017.
 */

@AppRegisterGenerator(
        packageName = "com.example.stream.jx",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
