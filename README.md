# joice项目简介    
+ joice是使用Spring框架开发的分布式系统架构。
+ 使用Maven对项目进行模块化管理，提高项目的易开发性、扩展性。
+ 公共功能：AOP、缓存、基类、公共配置、工具类等。
+ 系统功能：权限控制、调度管理、分布式事务、RPC。
+ 扩展性：可动态扩展集群节点，节点之间通过Dubbo和MQ进行通信。

# 主要功能
1.数据库：Druid数据库连接池，监控数据库访问性能，统计SQL执行性能，数据库密码加密。    
2.持久层：MyBatis持久化，使用MyBatis-Plus优化，减少SQL开发量；AOP切换数据库实现读写分离。    
3.MVC：基于Spring MVC注解，REST风格Controller。Exception统一管理。    
4.调度：Spring + Quartz，可以查询、修改周期、暂停、删除、新增、立即执行、查询执行记录等。    
5.基于Session的国际化提示信息，责任链模式的本地语言拦截器；Shiro登录、URL权限管理、会话管理、强制结束会话。    
6.缓存和Session：注解redis缓存数据，Spring-session和redis实现分布式session同步，重启服务会话不丢失。    
7.多系统交互：Dubbo、ActiveMQ多系统交互。    
8.日志：log4j2打印日志，同时基于时间和文件大小分割日志文件。
