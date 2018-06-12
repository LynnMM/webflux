package org.lynn.springboot2.webflux.router;


import org.springframework.http.MediaType;
import org.lynn.springboot2.webflux.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 21:11
 */
@Configuration
public class AllRouter {
  @Bean
  RouterFunction<ServerResponse> userRouter(UserHandler handler){
    return RouterFunctions.nest(
        // 相当于类上面的@RequestMapping("/user")
        RequestPredicates.path("/user"),
        // 下面的相当于类里面的@RequestMapping
        RouterFunctions.route(RequestPredicates.GET("/"), handler::getAllUser)
            .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::createUser)
            .andRoute(RequestPredicates.DELETE("/{id}"), handler::deleteUserById));
  }
}
