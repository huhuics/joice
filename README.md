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

8. 多系统交互：Dubbo、ActiveMQ多系统交互。    

9. 日志：logback打印日志，同时基于时间和文件大小分割日志文件。    


# 启动    
[joice-service] --> Run as --> Maven build... --> tomcat7:run

### 代码在逐步完善中
