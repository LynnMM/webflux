package org.lynn.springboot2.webflux.handler;

import org.lynn.springboot2.webflux.domain.User;
import org.lynn.springboot2.webflux.repository.UserRepository;
import org.lynn.springboot2.webflux.util.CheckUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 20:52
 */
@Component
public class UserHandler {
  private final UserRepository repository;

  public UserHandler(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * 得到所有用户
   * @param request
   * @return
   */
  public Mono<ServerResponse> getAllUser(ServerRequest request){
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(this.repository.findAll(), User.class);
  }

  /**
   * 创建用户
   * @param request
   * @return
   */
  public Mono<ServerResponse> createUser(ServerRequest request){
    Mono<User> user = request.bodyToMono(User.class);
    return user.flatMap(u -> {
      CheckUtil.checkName(u.getName());
      return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(this.repository.save(u), User.class);
    });
  }

  /**
   * 根据Id删除用户
   * @param request
   * @return
   */
  public Mono<ServerResponse> deleteUserById(ServerRequest request){
    String id = request.pathVariable("id");
    return this.repository.findById(id).flatMap(u -> this.repository.delete(u).then(ServerResponse.ok().build()))
        .switchIfEmpty(ServerResponse.notFound().build());
  }
}
