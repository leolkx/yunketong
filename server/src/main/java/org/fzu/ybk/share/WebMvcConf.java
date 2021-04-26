package org.fzu.ybk.share;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//@EnableWebMvc
@Configuration
public class WebMvcConf implements WebMvcConfigurer {
    @Autowired
    private UserSecurityInterceptor securityInterceptor;

    private final Logger logger = LoggerFactory.getLogger(WebMvcConf.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(securityInterceptor).addPathPatterns("/**");//配置登录拦截器拦截路径
        registry.addInterceptor(securityInterceptor)
                .excludePathPatterns("/**");
//                .addPathPatterns("/**")
//                .excludePathPatterns("/signin")
//                .excludePathPatterns("/signup")
//                .excludePathPatterns("/verification/**")
//                .excludePathPatterns("/signup/users")
//                .excludePathPatterns("/device")
//                .excludePathPatterns("/dataDictionary")
//                .excludePathPatterns("/userLogin/login")
//                .excludePathPatterns("/role/getAdminInfo")
//                .excludePathPatterns("/swagger-ui.html#/**")
//                .excludePathPatterns("/role/getUserInfo");


//        log.debug("跨域拦截器注册成功！");
    }

//    但是使用此方法配置之后再使用自定义拦截器时跨域相关配置就会失效。
//    原因是请求经过的先后顺序问题，当请求到来时会先进入拦截器中，而不是进入Mapping映射中，所以返回的头信息中并没有配置的跨域信息。浏览器就会报跨域异常。
//
//    正确的解决跨域问题的方法时使用CorsFilter过滤器。代码如下：

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
        // registry.addMapping("/api/**")
                // .allowedOrigins("*")
                // .allowedMethods("GET", "POST", "OPTIONS","PUT","DELETE")
                // .allowedHeaders("*")
                // .allowCredentials(true)
                // .maxAge(3600);
    // }



    // 自己添加转化器，不加bufferImg，
    // 在AbstractMessageConverterMethodProcessor
    /*

            if (body != null) {
                Set<MediaType> producibleMediaTypes = (Set)inputMessage.getServletRequest().getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
                if (!isContentTypePreset && CollectionUtils.isEmpty(producibleMediaTypes)) {
                    throw new HttpMediaTypeNotAcceptableException(this.allSupportedMediaTypes);
                }

                throw new HttpMessageNotWritableException("No converter for [" + valueType + "] with preset Content-Type '" + contentType + "'");
            }
             找不到转化器
    * */

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {


        BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter = new BufferedImageHttpMessageConverter();

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
                StandardCharsets.UTF_8);

        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */

        // 普通json的做法如下
//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        设置date转化
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        converters.add(jackson2HttpMessageConverter);


        //fastjson的做法如下 记得把jackson的包清理干净，否则会启动依赖报错

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        //必须为fastJson设置Media支持
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);

        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        //可不用,配置输出的一些特性，如null输出[] 等等..
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullNumberAsZero,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteNullBooleanAsFalse,
//                SerializerFeature.WriteNonStringKeyAsString,
//                SerializerFeature.BrowserCompatible,
//                SerializerFeature.WriteDateUseDateFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);

        // ToStringSerializer 同时一定要用fastjson提供的instance
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);



        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(bufferedImageHttpMessageConverter);

        converters.add(stringHttpMessageConverter);

        converters.add(fastJsonHttpMessageConverter);




    }


}