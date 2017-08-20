package com.example.stream.jx.generator;

import com.example.annotation.PayEntryGenerator;
import com.example.stream.core.wechat.template.WXPayEntryTemplate;

/**
 * Created by StReaM on 8/20/2017.
 */
@PayEntryGenerator(
        packagename = "com.example.stream.jx",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
