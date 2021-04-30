package coding.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义注解存活时间:运行时候
 * 表示在运行时候可以通过反射拿到
 * RunTime :运行时
 * Class ：编译时
 * Source ：只在源代码中，编辑阶段不存在
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * 定义注解使用在什么上面
 */
@Target(ElementType.TYPE)
public @interface Aspect {
    public Class type();
}
