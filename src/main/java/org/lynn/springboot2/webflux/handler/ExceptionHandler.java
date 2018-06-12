package org.lynn.springboot2.webflux.handler;

import org.lynn.springboot2.webflux.exception.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 21:39
 */
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler{

  @Override
  public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
    ServerHttpResponse response = serverWebExchange.getResponse();
    // 设置响应头400
    response.setStatusCode(HttpStatus.BAD_REQUEST);
    // 设置返回类型
    response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

    // 异常信息
    String errorMsg = toStr(throwable);

    DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());

    return response.writeWith(Mono.just(db));
  }

  private String toStr(Throwable throwable) {
    // 已知异常
    if (throwable instanceof CheckException){
      CheckException checkException = (CheckException)throwable;
      return String.format("%s:invalid value %s", checkException.getFieldName(), checkException.getFieldValue());
    }
    // 未知异常
    else{
      throwable.printStackTrace();
      return throwable.toString();
    }
  }
}
