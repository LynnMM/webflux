package org.lynn.springboot2.webflux.exception;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 20:24
 */
public class CheckException extends RuntimeException{
  private static final long serivalVersionUID = 1L;

  /**
   * 出错字段的名字
   */
  private String fieldName;

  /**
   * 出错字段的值

   */
  private String fieldValue;

  public CheckException(String name, String value){
    this.fieldName = name;
    this.fieldValue = value;
  }

  public CheckException() {
    super();
  }

  public CheckException(String message) {
    super(message);
  }

  public CheckException(String message, Throwable cause) {
    super(message, cause);
  }

  public CheckException(Throwable cause) {
    super(cause);
  }

  protected CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldValue() {
    return fieldValue;
  }

  public void setFieldValue(String fieldValue) {
    this.fieldValue = fieldValue;
  }
}
