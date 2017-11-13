# 目录
- [目录](#目录)
- [一. joice项目简介](#一-joice项目简介)
- [二. 主要功能](#二-主要功能)
    - [1.数据库](#1数据库)
    - [2.持久层](#2持久层)
    - [3.MVC](#3mvc)
    - [4.调度](#4调度)
    - [5.session](#5基于session的国际化提示信息责任链模式的本地语言拦截器)
    - [6.Shiro](#6shiro登录url权限管理会话管理强制结束会话)
    - [7.缓存和Session](#7缓存和session)
    - [8.多系统交互](#8多系统交互)
    - [9.日志](#9日志)
    - [10.缓存](#10缓存)
        - [10.1 数据放入缓存](#101-数据放入缓存)
        - [10.2 修改缓存](#102-修改缓存)
        - [10.3 缓存设计关键点](#103-缓存设计关键点)
- [三. 启动](#三-启动)


# 一. joice项目简介
+ joice是使用Spring框架开发的分布式系统架构。
+ 使用Maven对项目进行模块化管理，提高项目的易开发性、扩展性。
+ 公共功能：AOP、缓存、基类、公共配置、工具类等。
+ 系统功能：权限控制、调度管理、分布式事务、RPC。
+ 扩展性：可动态扩展集群节点，节点之间通过Dubbo和MQ进行通信。

# 二. 主要功能
## 1.数据库
Druid数据库连接池，监控数据库访问性能，统计SQL执行性能，数据库密码加密。

+ 数据库密码加密和解密用到了`com.alibaba.druid.filter.config.ConfigTools`，再定一个类扩展`com.alibaba.druid.pool.DruidDataSource`即可，具体代码实现在`org.joice.service.datasource.DecryptDruidDataSource`    

## 2.持久层
MyBatis持久化；AOP切换数据库实现读写分离。

+ 读写分离实际上是动态切换数据库。扩展`org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource`，在每次数据库调用前确定数据源。具体代码实现在`org.joice.service.aspect.ChooseDataSource`    
    
## 3.MVC
基于Spring MVC注解，REST风格Controller。Exception统一管理。    

## 4.调度
Spring + Quartz，可以查询、修改周期、暂停、删除、新增、立即执行、查询执行记录等。

+ [Quartz知识点总结](https://github.com/huhuics/Accumulate/blob/master/%E6%9E%B6%E6%9E%84%E5%92%8C%E7%AE%97%E6%B3%95/Quartz%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5.md)

+ 具体实现在`org.joice.service.support.scheduler.SchedulerManager`
     
## 5.基于Session的国际化提示信息，责任链模式的本地语言拦截器
*未完*

## 6.Shiro登录、URL权限管理、会话管理、强制结束会话
*未完*

## 7.缓存和Session
*未完* 

注解redis缓存数据，Spring-session和redis实现分布式session同步，重启服务会话不丢失。  

## 8.多系统交互
Dubbo、RocketMQ多系统交互。

+ [RocketMQ知识点总结](https://github.com/huhuics/Accumulate/blob/master/%E6%9E%B6%E6%9E%84%E5%92%8C%E7%AE%97%E6%B3%95/RocketMQ%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5.md)

## 9.日志
logback打印日志，同时基于时间和文件大小分割日志文件。    

## 10.缓存
+ 本地缓存：基于`ConcurrentHashMap`实现，实现类在[MapCache](https://github.com/huhuics/joice/blob/master/joice-cache/src/main/java/org/joice/cache/map/MapCache.java)。[MapCacheDaemon](https://github.com/huhuics/joice/blob/master/joice-cache/src/main/java/org/joice/cache/map/MapCacheDaemon.java)是一个守护线程，支持对Map的缓存持久化、缓存失效策略等等，使用方式可参考 [MapCache测试用例](https://github.com/huhuics/joice/blob/master/joice-cache/src/test/java/org/joice/cache/test/MapCacheTest.java)

+ Redis缓存：基于`ShardedJedis`实现，实现类在[ShardedJedisCache](https://github.com/huhuics/joice/blob/master/joice-cache/src/main/java/org/joice/cache/redis/ShardedJedisCache.java)

开发这个缓存中间件的初衷是为了减少缓存操作的代码与业务逻辑解耦，借鉴`Spring Cache`的思想使用`AOP + Annotation`等技术实现缓存与业务逻辑的解耦，在需要对查询结果进行缓存的地方，使用`org.joice.cache.annotation.Cacheable`标记。

### 10.1 数据放入缓存
```java
    @Cacheable
    public BizPayOrder getById(Long id) {
        BizPayOrder order = bizPayOrderMapper.selectByPrimaryKey(id);
        LogUtil.info(logger, "订单查询结果,order={0}", order);
        return order;
    }

    @Cacheable(key = "'payOrderService_getById_'+#args[0].id", condition = "#args[0].id>3")
    public BizPayOrder getById(BizPayOrder order) {
        Long id = order.getId();
        BizPayOrder ret = bizPayOrderMapper.selectByPrimaryKey(id);
        LogUtil.info(logger, "订单查询结果,order={0}", ret);
        return ret;
    }
```    

+ 如果不自定义key，则该缓存使用自动生成的key。生成的规则是将类名、方法名、参数值一起计算其hashcode，这也意味着如果使用默认生成的key将不支持删除。 **注意：** 如果是使用自动生成key，切点处的方法参数如果不是基本类型而是对象，则该对象必须继承`org.joice.cache.to.BaseTO`，这样只要参数值每次一致，生成的hashcode就是相同的。

+ 此外，key支持Spring EL表达式，condition也支持。

+ 为了尽量减少内存使用和对网络带宽的压力，`joice-cache`实现了基于`Hessian`的序列化工具，开发者也可以通过实现`org.joice.cache.serializer.Serializer<T>`接口自行扩展    

### 10.2 修改缓存
```java
    @CacheDel(items = { @CacheDelItem(key = "'payOrderService_getById_'+#args[0].id") }, condition = "#retVal == true")
    public boolean updateOrder(BizPayOrder order) {
        return bizPayOrderMapper.updateByPrimaryKey(order) == 1;
    }
```    
+ 当需要修改缓存时，为避免缓存与数据库双写不一致，采取的策略是先修改数据库，成功以后再删除缓存。[为什么？](https://github.com/huhuics/Accumulate/blob/master/%E6%9E%B6%E6%9E%84%E5%92%8C%E7%AE%97%E6%B3%95/%E7%BC%93%E5%AD%98%E6%9B%B4%E6%96%B0%E5%A5%97%E8%B7%AF.md)

+ 实际业务场景中，修改一条数据库数据可能涉及删除多条缓存数据，故`@CacheDel`注解中需要设置`@CacheDelItem`数组，一个`@CacheDelItem`表示一条需要删除缓存的数据

+ 当修改数据库成功以后才能删除缓存，`@CacheDel`可以通过设置`condition`来控制，`condition`支持Spring EL表达式

### 10.3 缓存设计关键点
#### 10.3.1 缓存是否会满，满了怎么办？
理论上来说，随着缓存数据的日益增多，在容量有限的情况下，缓存肯定有一天会满，如何应对？    
① 选择合适的缓存丢弃策略，如FIFO、LRU、LFU    
② 针对当前设置的容量，设置适当警戒值，如10G缓存，当缓存数据到达8G时发出报警，提前扩容    
③ 给缓存设置过期时间    

#### 10.3.2 缓存是否允许丢失？丢失了怎么办？
根据业务场景判断是否允许丢失，如果不允许，需要带持久化功能的缓存服务来支持，如Redis，还可根据业务对丢失时间的容忍度，选择更具体的持久化策略，比如Reids的RDB和AOF。    

#### 10.3.3 缓存被“击穿”问题
+ **概念：** 缓存在某个时间点过期，恰好在这个时间点对这个key有大量的并发请求过来，这些请求发现缓存过期一般都会从数据库加载数据并回设缓存，这个时候大并发的请求可能会瞬间把后端数据库压垮

+ **如何解决：** 比较常用的做法是使用mutex。简单来说就是当缓存失效时，不是立即去lodad DB，而是先使用缓存工具的某些带成功操作返回值的操作（比如Redis的SETNX命令）去set一个mutex key，当操作返回成功时，再进行load DB的操作并回设缓存；否则就重试get缓存的方法，算法伪代码如下：    

```java
public String get(key) {
      String value = redis.get(key);
      if (value == null) { //缓存值过期
          //设置超时时间，防止del操作失败的时候，下次缓存过期一直不能load db
          if (redis.setnx(key_mutex, 1, 3 * 60) == 1) {  //代表设置成功
               value = db.get(key);
                      redis.set(key, value, expire_secs);
                      redis.del(key_mutex);
              } else {  //这个时候代表同时候的其他线程已经load db并回设到缓存了，这时候重试获取缓存值即可
                      sleep(50);
                      get(key);  //重试
              }
          } else {
              return value;      
          }
  }
````

# 三. 启动
先在MySQL中导入`joice.sql`文件，然后再在`joice-service`的`resources`-->`config`修改成你自己的配置文件。    
本项目需要依赖`Zookeeper`,`ActiveMQ`    

> [joice-service] --> Run as --> Maven build... --> tomcat7:run

### _代码在逐步完善中_
