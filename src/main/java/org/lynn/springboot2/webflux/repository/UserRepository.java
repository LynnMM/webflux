package org.lynn.springboot2.webflux.repository;

import org.lynn.springboot2.webflux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 14:59
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String>{

  /**
   * 根据年龄查找用户
   * @param start
   * @param end
   * @return
   */
  Flux<User> findByAgeBetween(int start, int end);
}
