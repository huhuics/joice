# Joice
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


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
    - [8.RocketMQ](#8rocketmq)
        - [8.1 错误的方案](#81-错误的方案)
        - [8.2 方案1——业务方自己实现](#82-方案1业务方自己实现)
        - [8.3 方案2——RocketMQ事务消息](#83-方案2rocketmq事务消息)
        - [8.4 顺序消息](#84-顺序消息)
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

## 8.RocketMQ
[RocketMQ知识点总结](https://github.com/huhuics/Accumulate/blob/master/%E6%9E%B6%E6%9E%84%E5%92%8C%E7%AE%97%E6%B3%95/RocketMQ%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5.md)

对于分布式事务的实现，常见的一致性算法（如二阶段提交）耗时长、效率低，一般使用MQ来实现数据的最终一致性。

> 大事务 = 小事务 + MQ

以转账为例：有两个账号要进行转账操作，两个账号分属A、B两个不同系统，有两个不同的数据库，A要扣钱，B要加钱，如何保证数据一致性？

根据最终上面的思路：A系统扣钱，然后发消息给中间件，B系统接收此消息，进行加钱。但这里有两个问题：A是先扣钱，后发送消息？还是先发送消息，后扣钱？

**假设A先扣钱，再发送消息** 如果扣钱成功，但网络异常，发送消息失败，重试依然失败，怎么办？

**假设A先发送消息，再扣钱** 如果消息发送成功，但扣钱失败，消息又不能撤回，怎么办？

结论：只要发送消息和扣钱这两个操作不是原子的，无论谁先谁后，都是有问题的。那这个问题应该如何解决？

### 8.1 错误的方案
把“发送消息”这个网络调用和扣钱放在同一个事务中，如果发送消息失败，扣钱自动回滚，这不就保证这两个操作的原子性吗？这个方案是错误的，理由如下：

+ 发送消息失败，发送方并不知道消息中间件是真的没有收到，还是消息已经收到，只是返回response的时候失败了

    如果是已经收到了消息，但返回response的时候失败，如果A系统执行回滚，则会导致A系统的钱没有扣，B系统的钱却增加了
    
+ 网络调用放在DB事务前面，可能会因为网络的延时，而导致DB长事务，严重的时候会阻塞整个DB，风险很大
  
### 8.2 方案1——业务方自己实现
如果消息中间件没有“事务消息”功能，例如使用的是Kafka，那如何解决这个问题？

解决方案如下：

    (1) Producer端准备一张消息表，把扣钱update DB和插入消息这两个操作，放在一个事务里面

    (2) 准备一个后台程序，定时把消息表中的message发送给消息中间件，失败了，就不断重试，允许消息重复

    (3) Consumer接收消息并做好幂等处理

该方案的缺点是需要额外设计消息表，同时还要开发一个后台任务扫码本地消息，这将导致消息处理和业务逻辑耦合。

### 8.3 方案2——RocketMQ事务消息
RocketMQ的一个特性是能够发送“事务消息”，既解决上文中问题，又不和业务耦合。

RocketMQ把消息的发送分成了2个阶段：**Prepare阶段** 和 **确认阶段**，具体来说分为3个步骤：

    (1) 发送Prepared消息

    (2) 执行本地事务

    (3) 根据本地事务执行结果成功或失败，确认或取消Prepared消息

如果前两步成功了，最后一步失败，怎么办？RocketMQ会定期扫描所有Prepared消息，询问发送方消息是确认发出去还是取消发送？（RocketMQ 3.2版本以后取消了消息回查功能）

在**joice**中，写了一个数据最终一致性的例子：

用户下单购买商品（http://localhost:8089/joice-web/order/toOrder）， 系统需要完成两件事：扣减用户账户余额和创建订单。这两个动作显然要是原子的。**joice-web**完成扣减用户余额，**joice-service**完成创建订单。joice-web是消息的发送者，joice-service是消息的消费者。示意图如下：

![](https://github.com/huhuics/Accumulate/blob/master/image/%E4%BA%8B%E5%8A%A1%E6%B6%88%E6%81%AF%E5%8F%91%E9%80%81%E5%92%8C%E6%B6%88%E8%B4%B9.png)

### 8.4 顺序消息
保证消息有序，最简单的方法是生产者--MQ--消费者都是一对一对一的关系。但这个限制会制约消息中间件的吞吐量，通常情况下消费者都是多个的，因此存在消费者重复消费的情况，RocketMQ要求客户端自己做消费幂等控制。那么RocketMQ是如何实现顺序消息的呢？

![](https://github.com/huhuics/Accumulate/blob/master/image/RocketMQ%E9%A1%BA%E5%BA%8F%E6%B6%88%E6%81%AF.png)

默认情况下，生产者采用轮询的方式将消息投递到每个队列，每个消费者实例根据订阅的Topic平均分配每个consumer queue，同一个queue中，RocketMQ保证消息是FIFO顺序的。这样，在生产者端，如果能把顺序消息发送到同一个consumer queue中，那么消息就是有序的。下面以下单这一操作说明生产者顺序消息是如何发送的：

```java
    //RocketMQ通过MessageQueueSelector中实现的算法来确定消息发送到哪一个队列上
    //RocketMQ默认提供了两种MessageQueueSelector实现：随机/Hash
    //此处根据业务实现自己的MessageQueueSelector：订单号相同的消息会被先后发送到通过一个队列中
    SendResult result = rocketMqProducer.getDefaultMQProducer().send(message, new MessageQueueSelector() {
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) { //此处的arg参数就是下面传入的orderId
            int id = (int) arg;
            int index = id % mqs.size();
            return mqs.get(index);
        }
    }, orderId);
```

把订单号取模运算，以此确定此消息投递到哪个队列中，即：相同订单号的消息 ----> 有相同取模结果 ----> 投递到相同队列。结果可能是这样的：

![](https://github.com/huhuics/Accumulate/blob/master/image/RocketMQ%E9%A1%BA%E5%BA%8F%E6%B6%88%E6%81%AF-1.png)

joice开发了一个顺序消息例子，模拟用户注册并发送两个消息，一个消息是发往邮件系统，另一个是发往账户系统。消息生产者是[UserRegisterMqProducerService](https://github.com/huhuics/joice/blob/master/joice-web/src/main/java/org/joice/mvc/service/UserRegisterMqProducerService.java)

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
