# spring作用

## 简化Java开发

### 简化策略

#### 基于POJO的轻量级和最小侵入性开发

特点是不同于其他框架会要求类实现框架相关的接口，比如EJB要求实现SessionBean接口。

#### 通过依赖注入和面向接口实现松耦合

面向接口，而不是面向具体的类，这样在代码中使用的是这个接口，他的实现类可以是不固定的随意切换的。

面向接口：

```java
public interface EmailService {
    void sendEmail(String to, String message);
}

public class SmtpEmailService implements EmailService {
    @Override
    public void sendEmail(String to, String message) {
        // 具体的发送邮件实现
        System.out.println("Sending SMTP email to " + to + " with message: " + message);
    }
}

```

依赖注入：一种设计模式？将对象的依赖关系在外部进行配置而不是在类内部通过代码创建。

如下示例中就是使用配置文件或注解（下文）来配置依赖注入。

```java
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailService implements EmailService {
    @Override
    public void sendEmail(String to, String message) {
        System.out.println("Sending SMTP email to " + to + " with message: " + message);
    }
}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    private final EmailService emailService;

    @Autowired
    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notify(String to, String message) {
        emailService.sendEmail(to, message);
    }
}
```

使用注解配置时候，还需要一个注解配置类，启动组件的扫描，才能将以上内容扫描进去

```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}

```

#### 基于切面和惯例进行声明式编程

声明式编程：开发者只定义了程序的逻辑和期望结果，具体的指向细节和流程由编程语言或者框架决定。

例子：SQL；切面编程

#### 通过切面和模板减少样板式代码

非功能代码。

# spring核心功能模块

## 核心spring容器

spring框架最基础部分，提供依赖注入来实现容器对Bean的管理，依赖注入通过控制反转（IOC）容器实现。

Spring自带了几种容器实现：

- Bean工厂（BeanFactory
- 应用上下文（ApplicationContext

### 作用域：

- singleton，在IOC容器中只有一个Bean实例
- prototype，每次调用bean都会返回一个新的实例
- request，每次HTTP请求都会创建一个新的Bean，仅仅适用于web的Spring WebApplicationContext环境
- session，同一个Http Session共享一个bean，适用范围同上
- application，限制一个bean的作用域为servletContext的生命周期。作用域同上

### BeanFactory

负责管理和创建应用程序中的Bean（即Java对象）。提供了Bean的定义、实例化、配置以及依赖注入等功能。

基本实现流程如下：

1.读取配置



2.bean的定义


3.Bean实例化与依赖注入

调用BeanFactory的getBean方法时，如果Bean没有实例化，会间根据定义信息通过反射创建实例并处理依赖关系，将其他bean注入到所需的Bean中。

4.延迟初始化

BeanFactory支持懒加载或延迟初始化，只有在请求某个Bean时才会实例化他。

### ApplicationContext

建立在BeanFactory上的容器，在他的基础上做了增强，比如

- 事件传播
- 资源加载-可以从多种资源位置如文件系统、类路径加载资源
- 自动装配-支持基于注解的自动装配

#### 举例

- ClassPathXmlApplicationContext--类路径下的xml配置文件中加载上下文定义
- FileSystemXmlApplicationContext-- 文件系统下的xml配置文件并加载上下文定义
- XmlWebApplicationContext-- 读取web应用下的XML配置文件并装载上下文定义

使用：

```java
ApplicationContext context = new FileSystemXmlApplicationContext("c:/a.xml"); 
```

## 区别

BeanFactory采用延迟加载，getBean时候才会对Bean进行加载实例化。

ApplicationContext则是在容器启动时候，一次性创建所有的Bean。这样可以在容器启动时就发现存在的配置错误

ApplicationContext内存占用比较多。

BeanFactory只有最基本的依赖注入支持。

ApplicationContext完善了Bean生命周期管理机制-允许通过回调接口控制Bean的初始化和销毁过程。

## aop模块

## ORM

对象关系映射模块，简化Java对象与关系数据库之间的数据转换和操作，支持刘翔的ORM框架，比如Mybatis等

## DAO

用于简化JDBC，为常见的数据库操作提供了简化接口和模板类，使得可以轻松执行数据库查询、更新和事务管理操作而无需编写大量的样板代码。

## web和远程调用

## Spring Context

## spring mvc

核心：

dispatcherServlet-前端控制器，用于根据URL、参数分发请求到相应的控制器。

controller-控制器，通过@RequestMapping来映射url路径。

model-在控制器和试图之间传递数据。

view-将模型数据呈现给用户。

# spring bean加载流程&生命周期

![1722176984797.png](./1722176984797.png)

- 实例化一个Bean对象
- 为Bean设置相关属性和依赖
- 初始化
- 销毁

整体流程：

- Spring对Bean实例化。
- Spring将值和Bean的引用注入到Bean相应属性。
- 如果Bean实现了BeanNameAware接口将Bean的Id传给setBeanName接口方法。
- 如果Bean实现了BeanfactoryAware接口将调用setBeanFactory方法将Beanfactory容器实例传入。
- 如果实现了ApplicationContextAware接口，Spring将调用setApplicationContext()接口方法，将应用上下文的引用传入。
- 如果实现了BeanPostProcessor接口，将调用postProcessBeforeInitialization()接口方法。
- 如果实现了InitializationBean接口，将调用他们的afterPropertiesSet()接口方法。
- 如果使用了init-method生命初始化方法，该方法也会被调用。
- 如果实现了BeanPostProcessor接口，将调用postProcessAfterInitialization()接口方法试。
- 如果实现了DisposableBean接口，Spring将调用他的destroy()接口方法。
- 如果使用destroy-method生命了销毁方法，该方法也会被调用。

代码源码：

```java
// AbstractAutowireCapableBeanFactory.java
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args)
    throws BeanCreationException {
 
    // 1. 实例化
    BeanWrapper instanceWrapper = null;
    if (instanceWrapper == null) {
        instanceWrapper = createBeanInstance(beanName, mbd, args);
    }
   
    Object exposedObject = bean;
    try {
        // 2. 属性赋值
        populateBean(beanName, mbd, instanceWrapper);
        // 3. 初始化
        exposedObject = initializeBean(beanName, exposedObject, mbd);
    }
 
    // 4. 销毁-注册回调接口
    try {
        registerDisposableBeanIfNecessary(beanName, bean, mbd);
    }
 
    return exposedObject;
}
```

初始化代码：

```java
if(System.getSecurityManager() != null)
{
    AccessController.doPrivileged((PrivilegedAction < Object > )() - >
    {
        invokeAwareMethods(beanName, bean);
        return null;
    }, getAccessControlContext());
}
else
{
    invokeAwareMethods(beanName, bean);
} // 
4. BeanPostProcessor 前置处理 Object wrappedBean = bean;
if(mbd == null || !mbd.isSynthetic())
{
    wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
} // 
5. 若实现 InitializingBean 接口， 调用 afterPropertiesSet() 方法 // 
6. 若配置自定义的 init - method方法， 则执行
try
{
    invokeInitMethods(beanName, wrappedBean, mbd);
}
catch(Throwable ex)
{
    throw new BeanCreationException((mbd != null ? mbd.getResourceDescription() : null), beanName, "Invocation of init method failed", ex);
} //
7. BeanPostProceesor 后置处理
if(mbd == null || !mbd.isSynthetic())
{
    wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
}
return wrappedBean;
}
```

销毁代码：

```java
 // DisposableBeanAdapter.java public void destroy() {     // 
 9. 若实现 DisposableBean 接口， 则执行 destory() 方法
 if(this.invokeDisposableBean)
 {
     try
     {
         if(System.getSecurityManager() != null)
         {
             AccessController.doPrivileged((PrivilegedExceptionAction < Object > )() - >
             {
                 ((DisposableBean) this.bean).destroy();
                 return null;
             }, this.acc);
         }
         else
         {
             ((DisposableBean) this.bean).destroy();
         }
     }
 } //
 10. 若配置自定义的 detory - method 方法， 则执行
 if(this.destroyMethod != null)
 {
     invokeCustomDestroyMethod(this.destroyMethod);
 }
 else if(this.destroyMethodName != null)
 {
     Method methodToInvoke = determineDestroyMethod(this.destroyMethodName);
     if(methodToInvoke != null)
     {
         invokeCustomDestroyMethod(ClassUtils.getInterfaceMethodIfPossible(methodToInvoke));
     }
 }
 }
```

# spring 控制反转IOC

IOC（控制反转）：一种设计思想，是Spring的核心。调用类的方法不是通过new操作，而是Spring配置来创建对象，然后交给IOC容器管理对象，使用时候找IOC容器要。

## 设计模式

### 工厂设计模式

### 单例模式

### 策略模式

一个类的行为可以在运行时改变。

当Bean需要访问资源配置文件时，有两种方式包括：代码中获取Resource实例；依赖注入。

在依赖注入的过程中会调用ApplicationContext获取resource实例。然而Resource接口封装了多种可能的资源类型，包括UrlResource以及ClassPathResource等，spring需要对不同资源采用不同的访问策略。在这Spring让ApplicationContext成为资源访问策略的决策者，直接使用getResource，而ApplicationContext调用getResource来获取资源。ApplicationContext 将会负责选择 Resource 的实现类，将应用程序和具体资源访问策略分开。

```java
ApplicationContext ctx = new Class PathXmlApplicationContext("bean.xml");
Resource res = ctx.getResource("book.xml");

```

### 装饰者模式

## 反射

这一段配置文件以及他的实现伪代码

```java
<bean id="courseDao" class="com.qcjy.learning.Dao.impl.CourseDaoImpl"></bean>
```

```java
//解析<bean .../>元素的id属性得到该字符串值为“courseDao”
String idStr = "courseDao";
//解析<bean .../>元素的class属性得到该字符串值为“com.qcjy.learning.Dao.impl.CourseDaoImpl”
String classStr = "com.qcjy.learning.Dao.impl.CourseDaoImpl";
//利用反射知识，通过classStr获取Class类对象
Class<?> cls = Class.forName(classStr);
//实例化对象
Object obj = cls.newInstance();
//container表示Spring容器
container.put(idStr, obj);
```

# spring依赖注入

对象不是在IOC容器中查找依赖类，而是在容器实例化对象的时候，已经将该对象所依赖的类注入给该对象。

## spring依赖注入的方式

### 基于注解的注入

```java
@Controller
public class HappyController {
    @Autowired //默认依赖的ClubDao 对象（Bean）必须存在
    //@Autowired(required = false) 改变默认方式
    @Qualifier("goodClubService")
    private ClubService clubService;
 
    // Control the people entering the Club
    // do something
}
```

### 构造方法注入

```java
<!--dao -->
<bean id="userTestDao" class="com.chenlw.java.web.utils.springwiki.UserTestDao">
</bean>
 
<!-- 注册userService -->
<bean id="userTestServiceImpl" class="com.chenlw.java.web.utils.springwiki.UserTestServiceImpl">
    <!-- 将DAO对象注入Service层 -->
    <constructor-arg ref="userTestDao"/>
</bean>
```

### setter注入

```java
<!-- 注册dao -->
<bean id="userTestDao" class="com.chenlw.java.web.utils.springwiki.UserTestDao">
</bean>
 
<!-- 注册userService -->
<bean id="userTestServiceImpl" class="com.chenlw.java.web.utils.springwiki.UserTestServiceImpl">
    <!-- 将DAO对象注入Service层 -->
    <property name="userTestDao" ref="userTestDao"/>
</bean>
```

## 何时进行依赖注入


当 Spring IOC 容器启动时完成定位、加载、注册操作，此时 IOC容器已经获取到 applicationContext.xml 配置文件中的全部配置，并以 BeanDefinition类的形式保存在一个名为：beanDefinitionMap 的 ConcurrentHashMap 中。如下所示

```java
//存储注册信息的BeanDefinition
private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

```

此时容器只存储了这些对象定义，并没有创建真正的实例对象没有进行依赖注入操作。

当lazy-init属性默认为false，此时会在启动Spring容器时完成实例对象创建，即Spring容器启动时触发依赖注入。

当该为true，容器在启动时不会完成实例对象的创建。只有用户第一次通过调用Spring的getBean方法时候，才会向IOC容器索要Bean对象，此时IOC容器才会触发依赖注入。

## 源码分析入手处

getBean方法

## 实例化的Bean对象用什么类型存储

FactoryBean：由spring生成出来的Bean就是FactoryBean。

BeanFactory：Bean工厂，创建Bean对象的工厂。

## @Autowired、@Resource与@Qualifier区分

## 作用

让相互写作的软件组件保持松散耦合

# spring切面

[Spring AOP全面详解(超级详细)-CSDN博客](https://blog.csdn.net/Cr1556648487/article/details/126777903)

## 作用

允许把遍布应用各处的功能分离出来形成可重用的组件。

## 概念


## 动态代理


# spring 事务

# Spring常用注解

## @Configuration，@Component，@Service，@Controller

# spring容器的存储结构
