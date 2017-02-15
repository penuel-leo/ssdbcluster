# ssdbcluster
=====================
## 包含ssdb-client,ssdb-proxy,ssdb-monitor三个模块
----------------------
### ssdb-client
>类Jedis功能及协议

1. 无歧义的集成所有ssdb command操作
2. server control commands
3. connetction Pooling
4. 客户端数据分片功能（待开放）

### ssdb-proxy
> ssdb-server的代理，做负载均衡，数据分片，server监控，数据迁移等功能

### ssdb-monitor
> ssdb server和client的UI管理界面,实时查看server和数据信息

### USE
#### ssdb-client
1. cd ssdbcluster directory
2. mvn clean install (-Dmaven.test.skip=true)
3. add pom dependency:
  ```xml
    <dependency>
        <groupId>com.yeahmobi.ssdbcluster</groupId>
        <artifactId>ssdb-client</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
  ```
4. you can use JSSDBPool directly such as *ssdb-client/test/java...JSSDBPoolTest*:
  ```java
    JSSDBPool pool = new JSSDBPool("172.0.0.1", 9801);
    try(JSSDB ssdb = pool.getResource()){
      ssdb.set(k,v);
    }
  ```
  you can package JSSDBPool use Singleton such as:*ssdb-client/test/java...JSSDBClientDemo*:
