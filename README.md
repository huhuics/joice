# joice项目简介    
+ joice是使用Spring框架开发的分布式系统架构。
+ 使用Maven对项目进行模块化管理，提高项目的易开发性、扩展性。
+ 公共功能：AOP、缓存、基类、公共配置、工具类等。
+ 系统功能：权限控制、调度管理、分布式事务、RPC。
+ 扩展性：可动态扩展集群节点，节点之间通过Dubbo和MQ进行通信。

# 主要功能    
1. 数据库：Druid数据库连接池，监控数据库访问性能，统计SQL执行性能，数据库密码加密。    
    + 数据库密码加密和解密用到了`com.alibaba.druid.filter.config.ConfigTools`，再定一个类扩展`com.alibaba.druid.pool.DruidDataSource`即可，具体代码实现在`org.joice.service.datasource.DecryptDruidDataSource`    

2. 持久层：MyBatis持久化；AOP切换数据库实现读写分离。    
    + 读写分离实际上是动态切换数据库。扩展`org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource`，在每次数据库调用前确定数据源。具体代码实现在`org.joice.service.aspect.ChooseDataSource`    
    
3. MVC：基于Spring MVC注解，REST风格Controller。Exception统一管理。    

4. 调度：Spring + Quartz，可以查询、修改周期、暂停、删除、新增、立即执行、查询执行记录等。    
    + [Quartz知识点总结](https://github.com/huhuics/Accumulate/blob/master/%E6%9E%B6%E6%9E%84%E5%92%8C%E7%AE%97%E6%B3%95/Quartz%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5.md)    
    + 具体实现在`org.joice.service.support.scheduler.SchedulerManager`
     
5. 基于Session的国际化提示信息，责任链模式的本地语言拦截器。    

6. Shiro登录、URL权限管理、会话管理、强制结束会话。    

7. 缓存和Session：注解redis缓存数据，Spring-session和redis实现分布式session同步，重启服务会话不丢失。    

8. 多系统交互：Dubbo、RocketMQ多系统交互。    
    + [RocketMQ知识点总结](https://github.com/huhuics/Accumulate/blob/master/%E6%9E%B6%E6%9E%84%E5%92%8C%E7%AE%97%E6%B3%95/RocketMQ%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5.md)

9. 日志：logback打印日志，同时基于时间和文件大小分割日志文件。    

10. 缓存    
    + 本地缓存：基于`ConcurrentHashMap`实现，实现类在[MapCache](https://github.com/huhuics/joice/blob/master/joice-cache/src/main/java/org/joice/cache/map/MapCache.java)。[MapCacheDaemon](https://github.com/huhuics/joice/blob/master/joice-cache/src/main/java/org/joice/cache/map/MapCacheDaemon.java)是一个守护线程，支持对Map的缓存持久化、缓存失效策略等等，使用方式可参考 [MapCache测试用例](https://github.com/huhuics/joice/blob/master/joice-cache/src/test/java/org/joice/cache/test/MapCacheTest.java)    
    + Redis缓存：基于`ShardedJedis`实现，实现类在[ShardedJedisCache](https://github.com/huhuics/joice/blob/master/joice-cache/src/main/java/org/joice/cache/redis/ShardedJedisCache.java)    
    
    开发这个缓存中间件的初衷是为了减少缓存操作的代码与业务逻辑解耦，借鉴`Spring Cache`的思想使用`AOP + Annotation`等技术实现缓存与业务逻辑的解耦，在需要对查询结果进行缓存的地方，使用`org.joice.cache.annotation.Cacheable`标记：    
    
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

+ 如果不自定义key，则该缓存使用自动生成的key。生成的规则是将类名、方法名、参数值一起计算其hashcode，这也意味着如果使用默认生成的key将不支持删除。    
+ 此外，key支持Spring EL表达式，condition也支持。    
+ 为了尽量减少内存使用和对网络带宽的压力，`joice-cache`实现了基于`Hessian`的序列化工具，开发者也可以通过实现`org.joice.cache.serializer.Serializer<T>`接口自行扩展    
# 启动    
先在MySQL中导入`joice.sql`文件，然后再在`joice-service`的`resources`-->`config`修改成你自己的配置文件。    
本项目需要依赖`Zookeeper`,`ActiveMQ`    

> [joice-service] --> Run as --> Maven build... --> tomcat7:run

### 代码在逐步完善中
