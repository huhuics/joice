# joice项目简介
该分支基于主分支`master`的joice项目进行修改、重写与扩展，目的在于学习主分支中使用到的技术与功能点。

## 已实现功能
- `joice-cache`缓存实现
    1. 以`ConcurrentHashMap`的方式实现本地缓存
        - 基本的set与get功能

- `joice-cache-test`缓存功能测试

# 启动
项目启动前需要对数据库连接进行配置。
[joice-cache-test] --> Run as --> Maven build... --> tomcat7:run
