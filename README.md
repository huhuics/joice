# joice项目简介
该分支基于主分支`master`的`joice`项目进行修改、重写与扩展，目的在于学习主分支中使用到的技术与功能点。

## joice-cache
提供`Cache`与`CacheManager`接口。

- Cache

    缓存的基本操作，包括：
    - set(key, value)
    - get(key)
    - delete(key)
    - deleteAll()
    - discard()
    - getConfig()
    
- CacheManager

    管理缓存，实现了Runnable接口，以守护线程的形式对缓存进行管理，包括：
    - cleanUpExpired()
    - persist()
    - restore()
    
## joice-cache-test
使用spring + springMVC + myBatis搭建的web应用，应用`joice-cache`的缓存以提供配置示例以及进行功能测试。

`spring-cache.xml`中配置如下：

```
<bean id="FIFO" class="org.springframework.beans.factory.config.FieldRetrievingFactoryBean">    
        <property name="staticField" value="com.sunveee.joice.cache.enums.CacheDiscardStrategyEnum.FIFO" />    
    </bean> 
                        
    <bean id="cacheConfig" class="com.sunveee.joice.cache.config.CacheConfig">
    	<!-- 命名空间 -->
    	<constructor-arg name="nameSpace" value="tomcat.joice-cache-test" />
    	<!-- 最大缓存数量 -->
    	<constructor-arg name="maxCacheNums" value="2" />
    	<!-- 缓存丢弃策略 -->
    	<constructor-arg name="cacheDiscardStrategyEnum" ref="FIFO" />
    	<!-- 持久化文件目录 -->
    	<constructor-arg name="persistFileFolderPath" value="D:\\项目\\缓存中间件\\tmp\\joice-cache" />
    	<!-- 持久化文件名 -->
    	<constructor-arg name="persistFilename" value="map.cache" />
    	<!-- 持久化时间间隔（也是清理过期缓存的时间间隔） -->
    	<constructor-arg name="persistTimeInterval" value="5" />
    </bean>
    
    <bean id="cache" class="com.sunveee.joice.cache.impl.local.map.MapCache">
    	<constructor-arg ref="cacheConfig" />
    </bean>
                        
    <bean id="cacheHandler" class="com.sunveee.joice.cache.handler.CacheHandler">
    	<constructor-arg ref="cache" />
    </bean>

	<bean id="cacheAspect" class="com.sunveee.joice.cache.aspect.CacheAspect" >
		<constructor-arg ref="cacheHandler" />
	</bean>

	<!-- 配置AOP -->
	<aop:config proxy-target-class="true">
		<aop:aspect ref="cacheAspect">
			<!-- 配置切点表达式 -->
			<aop:pointcut expression="execution(* com.sunveee.template.ssm.service.impl.*.*(..)) &amp;&amp; @annotation(com.sunveee.joice.cache.annotation.Cacheable)" id="busiPointcut" />
			<aop:around method="around" pointcut-ref="busiPointcut"/>
		</aop:aspect>
	</aop:config>
```

`UserServiceImpl.java`中配置如下：

```
@Cacheable(argRange = { 0 }, expireTime = 3)
@Override
public User getUserById(int userId) {
    LogUtil.info(logger, "数据库查询User,查询条件: userId={0}", userId);
    return this.userDao.selectById(userId);
}
```

## 已实现功能
- `joice-cache`缓存实现
    1. 以`ConcurrentHashMap`的方式实现本地缓存中间件
        - 基本的set与get功能
        - 添加了更多支持的配置，例如缓存namespace的配置，缓存最大容量以及丢弃策略等
        - 定时清理过期缓存功能
        - 缓存持久化与启动时从磁盘恢复功能

- `joice-cache-test`缓存功能测试

# 启动
项目启动前需要对数据库连接进行配置。
[joice-cache-test] --> Run as --> Maven build... --> tomcat7:run
