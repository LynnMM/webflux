package org.lynn.springboot2.webflux.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 14:08
 */
public class ReactorDemo {

  public static void main(String[] args) {
    // reactor = jdk8 stream + jdk9 reactive stream
    // Mono 0-1个元素
    // Flux 0-N个元素
    String[] strs = {"1", "2", "3"};

    // 定义订阅者
    Subscriber<Integer> subscriber = new Subscriber<Integer>(){
      private Subscription subscription;

      @Override
      public void onSubscribe(Subscription subscription){
        this.subscription = subscription;
        this.subscription.request(1);
      }

      @Override
      public void onNext(Integer item){
        System.out.println("接受到数据：" + item);
        this.subscription.request(1);

        // 或者 已经达到了目标， 调用cancel告诉发布者不再接受数据了
        // this.subscription.cancel();
      }

      @Override
      public void onError(Throwable throwable){
        throwable.printStackTrace();
        this.subscription.cancel();
      }

      @Override
      public void onComplete(){
        System.out.println("处理完了！");
      }
    };

    // jdk8的stream
    Flux.fromArray(strs).map(s -> Integer.parseInt(s))
    // 最终操作
    // jdk9的reactive stream
    .subscribe(subscriber);
  }
}
