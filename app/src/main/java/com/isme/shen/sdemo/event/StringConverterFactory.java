package com.isme.shen.sdemo.event;

import com.isme.shen.slibrary.utils.LogUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by shen on 2016/8/31.
 */
public class StringConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringConverter(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type,parameterAnnotations,methodAnnotations,retrofit);
    }

    private class StringConverter implements Converter<ResponseBody, String> {

        private Type type;
        public StringConverter(Type type) {
            this.type = type;
        }

        @Override
        public String convert(ResponseBody value) throws IOException {
            LogUtils.d("type","type；"+type);
            if(type == String.class){
                return value.string();
            }
            return null;//返回空会让后面的ConverterFactory 继续处理
        }
    }
}
