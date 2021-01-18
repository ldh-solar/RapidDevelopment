**项目说明** 
- 采用SpringBoot、MyBatis、SpringSecurity框架，搭建的一套基础模版，极低门槛，拿来即用。
- 提供了代码生成器，只需编写30%左右代码，其余的代码交给系统自动生成，可快速完成开发任务。

<br>

**具有如下特点** 
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 引入swagger文档支持，方便编写API接口文档
- 使用了lombok,减少代码量和提高可读性
- 使用前后端分离,通过引入jwt进行校验权限
- 引入了MybatisPlus,普通CRUD不用编写代码,提高效率
- 使用jasypt加密了yml配置文件,提高系统安全性
- 引入了Maven的docker插件,一键打包,构建,推送到阿里云镜像平台
- 配置拆分了dev和prod,打包时自动使用prod配置
- 整合[ELK](https://github.com/blank1993/springboot-elk)日志收集系统方便集群收集日志

<br>


**项目结构** 
```
example
├─java
│   │  
│   └──com.gddlkj.example
│          ├─ annotation 注解类
├          ├─ aop        切面类
├          ├─ config     配置信息
│          ├─ exception  自定义异常
│          ├─ filter     过滤器
├          ├─ mapper     mybatisMapper
│          ├─ model      数据对象
│          │    ├─ constants  枚举对象
│          │    ├─ domian     领域对象
│          │    ├─ dto        数据传输对象
│          │    ├─ mapper     mapstruct映射
│          │    └─ vo         视图对象 
│          ├─ security   权限相关
│          ├─ service    服务接口
│          ├─   └─impl   服务实现 
│          ├─ util       工具
│          └─ web.rest
│               ├─ admin  后台控制器
│               ├─ common 公共控制器
│               └─ front  前台控制器
│
│ 
├─resources  
│     ├─liquibase 脚本管理文件
│     ├─mapper    MyBatisXML文件
│     ├─templates 代码生成器模板（可增加或修改相应模板）
│     ├─404.jpg   找不到图片返回默认
│     ├─application.yml        全局配置文件
│     ├─application-dev.yml    开发环境配置文件
│     ├─application-prod.yml   发布环境配置文件
│     └─logback-spring.xml     日志输出配置
│
```

<br>

 **技术选型：** 
- 核心框架：Spring Boot 
- 安全框架：Spring-boot-starter-security
- 视图框架：Spring MVC
- 持久层框架：MyBatis-Plus
- 缓存框架： Spring-boot-starter-data-redis
- 文件服务器： MongoDB
- 数据库连接池：Druid
- 日志管理： SLF4J 、Logback
- 简化开发： Lombok
- API文档：  Swagger2
- 代码生成： Mybatis-plus-generator
- Token校验生成:  jjwt 0.9.0
- thumbnailator: 图片压缩
- Liquibase: 脚本管理
- elasticsearch: 全文检索引擎
- kibana: 日志收集图形界面
- logstash: 收集 解析 转换日志

<br>

 **软件需求** 
- JDK1.8
- Maven3.0+
- IDEA
- MySql 5.7
- Redis

<br>

 **本地部署**
- 通过git下载源码

- 安装MybatisX插件 File->Setting->Plugins->搜索MybatisX (可选)

- 安装Lombok插件   File->Setting->Plugins->搜索Lombok   (可选)

- 接口路径 http://localhost:8080

- swagger文档路径：http://localhost:8080/swagger-ui.html

<br>

**代码生成**
- 右键Test包里com.gddlkj.example.CodeGenerator Run main方法即可
- projectPath是生成的路径,注意是否开启了覆盖 
- 生成的vue文件夹是前端项目的代码,可以移动到前端然后删除本地,请勿提交
- 其他配置请看代码注释


**远程部署**


- 新方案使用Jenkins实现一键部署,通过手动点击部署http://192.168.0.213:9897/


<br>

**其他**
- 校验通过JWTAuthenticationFilter过滤器实现
- 重写了Controlle层模版增加了日志注解和5个基础Rest方法,并且加入Swagger注解(直接使用log.info()可以打印日志)
- 重写了Service层模版增加了日志注解和事务注解(直接使用log.info()可以打印日志,baseMapper可以直接使用,不用注入)
- 自定义了vue生成模版和前端api生成模版
- 定义了BaseController层提供返回数据快捷调用方法
- 定义了R返回实体,统一了返回格式,提供泛型可选用于显示swagger文档
- 定义了BaseEntity 抽取所有公共字段
- 默认生成代码不开启乐观锁和逻辑删除,需要使用可以使用注解 @TableLogic @Version开启
- 定义了全局异常处理ExceptionController,提供通过异常返回时间戳追踪,可以通过在Service层抛出BaseBusinessException返回错误信息
- 封装了参数校验直接在实体上使用注解即可,通过MethodArgumentNotValidException会自动处理
- 全局配置了日期参数格式化
- redis对数据字典和用户信息进行缓存,减少数据库查询压力
- 使用thumbnailator进行对图片输出压缩
- 支持前后台不同token校验模式,后台使用admin前缀,前台使用api前缀 
- @OperationLog 可以记录用户日志,具体使用参考UserController.login 方法
