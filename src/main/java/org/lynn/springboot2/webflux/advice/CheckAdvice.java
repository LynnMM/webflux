package org.lynn.springboot2.webflux.advice;

import org.lynn.springboot2.webflux.exception.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * 异常处理切面
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 20:10
 */
@ControllerAdvice
public class CheckAdvice {
  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity handleBindException(WebExchangeBindException e){
    return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CheckException.class)
  public ResponseEntity handleBindException(CheckException e){
    return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
  }

  /**
   * 把校验异常转换为字符串
   * @param ex
   * @return
   */
  private String toStr(WebExchangeBindException ex) {
    return ex.getFieldErrors().stream()
        .map(e -> e.getField() + ":" + e.getDefaultMessage())
        .reduce("", (s1, s2) -> s1 + "\n" + s2);
  }

  /**
   * 把校验异常转换为字符串
   * @param ex
   * @return
   */
  private String toStr(CheckException ex) {
    return String.format("%s:错误的值%s", ex.getFieldName(), ex.getFieldValue());
  }
}
