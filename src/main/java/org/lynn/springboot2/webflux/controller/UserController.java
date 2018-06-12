package org.lynn.springboot2.webflux.controller;

import org.lynn.springboot2.webflux.domain.User;
import org.lynn.springboot2.webflux.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 15:01
 */
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 以数组形式一次性返回数据
   * @return
   */
  @GetMapping("/")
  public Flux<User> getAll(){
    return userRepository.findAll();
  }

  /**
   * 以SSE形式多次返回数据
   * @return
   */
  @GetMapping(value = "/stream/all", produces = "text/event-stream")
  public Flux<User> streamGetAll(){
    return userRepository.findAll();
  }

  /**
   * 新增数据
   * @param user
   * @return
   */
  @PostMapping("/")
  public Mono<User> createUser(@RequestBody User user){
    // spring data jpa里面，新增和修改都是save， 有id是修改， id为空是新增
    // 根据实际情况是否置空id
    user.setId(null);
    return this.userRepository.save(user);
  }

  /**
   * 根据id删除用户
   * 存在的时候返回200，不存在返回404
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id")String id){
    // deleteById没有返回值，不能判断数据是否存在
    //this.userRepository.deleteById(id);
    return this.userRepository.findById(id)
        // 当你要操作数据，并返回一个Mono这个时候使用flatMap
        // 如果不操作数据，只是转换数据，使用map
        .flatMap(user -> this.userRepository.delete(user)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }

  /**
   * 修改数据
   * 存在的时候返回200和修改后的数据，不存在的时候返回404
   * @param id
   * @return
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @RequestBody User user){
    return this.userRepository.findById(id)
        // flatMap操作数据
        .flatMap(user1 -> {
          user1.setAge(user.getAge());
          user1.setName(user.getName());
          return this.userRepository.save(user1);
        })
        // map:转换数据
        .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
  }

  /**
   * 根据ID查找用户
   * 存在返回用户信息，不存在返回404
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id)
  {
    return this.userRepository.findById(id)
        .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
  }
}
