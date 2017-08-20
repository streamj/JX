package com.example.stream.jx.generator;

import com.example.annotation.EntryGenerator;
import com.example.stream.core.wechat.template.WXEntryTemplate;

/**
 * Created by StReaM on 8/20/2017.
 */

@EntryGenerator(
        packagename = "com.example.stream.jx",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
