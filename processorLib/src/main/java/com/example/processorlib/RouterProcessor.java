package com.example.processorlib;

import com.example.annatationlib.RouterTarget;
import com.example.commonlib.RouterByAnnotationManager;
import com.example.commonlib.RouterInjector;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * 自定义的编译期Processor，用于生成xxx$$Router.java文件
 */
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {
    /**
     * 文件相关的辅助类
     */
    private Filer mFiler;
    /**
     * 元素相关的辅助类
     */
    private Elements mElementUtils;
    /**
     * 日志相关的辅助类
     */
    private Messager mMessager;

    /**
     * 解析的目标注解集合
     */

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(RouterTarget.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mMessager.printMessage(Diagnostic.Kind.WARNING, "processprocessprocessprocess");

        Set<? extends Element> routeElements = roundEnv.getElementsAnnotatedWith(RouterTarget.class);
        for (Element element : routeElements) {

            String packageName = element.getEnclosingElement().toString();
            String fullClassName = element.toString();
            String className = fullClassName.substring(fullClassName.indexOf(packageName) + packageName.length() + 1, fullClassName.length());
            /**
             //             * 构建类
             //             */
            try {
                RouterTarget annotation = element.getAnnotation(RouterTarget.class);
                RouterByAnnotationManager.getInstance().addRouter(annotation.value(), element.toString());
                mMessager.printMessage(Diagnostic.Kind.WARNING, RouterByAnnotationManager.getInstance().getRouter(annotation.value()) + RouterByAnnotationManager.getInstance());

                FieldSpec routerKey = FieldSpec.builder(String.class, "routerKey", Modifier.FINAL, Modifier.PRIVATE).initializer("$S", annotation.value()).build();
                FieldSpec clazz = FieldSpec.builder(String.class, "fullClassName", Modifier.FINAL, Modifier.PRIVATE).initializer("$S", fullClassName).build();

                /**
                 * 构建方法
                 */
                MethodSpec methodSpec = MethodSpec.methodBuilder("injectRouter")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .addCode("com.example.commonlib.RouterByAnnotationManager.getInstance().addRouter($L,$L);", "routerKey", "fullClassName")
                        .build();


                TypeSpec finderClass = TypeSpec.classBuilder(className + "$$Router")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(methodSpec)
                        .addField(routerKey)
                        .addField(clazz)
                        .addSuperinterface(RouterInjector.class)
                        .build();

                JavaFile.builder(packageName, finderClass).build().writeTo(mFiler);
            } catch (Exception e) {
                error("processBindView", e.getMessage());
            }

        }
        return true;
    }

    public String getPackageName(TypeElement type) {
        return mElementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}