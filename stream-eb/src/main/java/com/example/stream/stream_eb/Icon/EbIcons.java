package com.example.stream.stream_eb.Icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by StReaM on 8/12/2017.
 */

public enum EbIcons implements Icon {
    icon_ali_pay('\ue67c'),
    ;

    private char character;

    EbIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return 0;
    }
}
