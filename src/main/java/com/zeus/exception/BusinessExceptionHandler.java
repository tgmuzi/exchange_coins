package com.zeus.exception;

import com.zeus.utils.AjaxObject;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.io.IOException;

/**
 * 异常处理器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public AjaxObject handleBusinessException(BusinessException e) {
        AjaxObject r = new AjaxObject();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        if (e.getData() != null) {
            r.put("data", e.getData());
        }

        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public AjaxObject handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return AjaxObject.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public AjaxObject handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return AjaxObject.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(IOException.class)
    public AjaxObject handleAuthorizationException(IOException e) {
        logger.error(e.getMessage(), e);
        return AjaxObject.error("IO读写异常");
    }

    @ExceptionHandler(Exception.class)
    public AjaxObject handleException(Exception e) {
        logger.error(e.getMessage(), e);
        // return AjaxObject.error(e.getMessage());
        return AjaxObject.error("系统错误，未知异常");// 禁止将系统内部错误直接抛出 比如SQL语句异常等
    }
}
