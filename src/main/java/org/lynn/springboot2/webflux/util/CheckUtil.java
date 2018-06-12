package org.lynn.springboot2.webflux.util;

import java.util.stream.Stream;
import org.lynn.springboot2.webflux.exception.CheckException;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 20:22
 */
public class CheckUtil {
  private static final String[] INVALID_NAMES = {"admin"};

  /**
   * 校验名字，
   * @param name
   */
  public static void checkName(String name){
    Stream.of(INVALID_NAMES)
        .filter(n -> n.equalsIgnoreCase(name))
        .findAny().ifPresent(n -> {
          throw new CheckException("name", name);
        });
  }

}
