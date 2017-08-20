package com.example.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created by StReaM on 8/20/2017.
 */

public final class EntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {
    static final String WE_CHAT_SPEC_NAME = "WXEntryActivity";
    static final String WE_CHAT_SPEC_PACKAGE = ".wxapi";
    static final String FILE_COMMENT = "微信入口文件";

    private Filer mFiler = null;
    private TypeMirror mTypeMirror = null;
    private String mPackageName = null;

    public void setFiler(Filer filer) {
        mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void aVoid) {
        mPackageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror t, Void aVoid) {
        mTypeMirror = t;
        generateJavaCode();
        return aVoid;
    }

    private void generateJavaCode() {
        final TypeSpec targetActivity = TypeSpec.classBuilder(WE_CHAT_SPEC_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(mTypeMirror))
                .build();

        final JavaFile javaFile = JavaFile.builder(mPackageName + WE_CHAT_SPEC_PACKAGE, targetActivity)
                .addFileComment(FILE_COMMENT)
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
