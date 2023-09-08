<scope>provided</scope>: 表示只在当前项目中使用,部署打包时不会被一并打包
    jsp-api、javax.servlet-api使用(因为这些东西Tomcat中有,部署时打包就显得多此一举,但编写时还是会用到 )
<scope>test</scope>: 表示仅在指定的包下才会生效,用于做测试
    junit使用


新出现的两个注解
    1.@RestController  用于@Controller类上
         如果一个类中所有请求都为Ajax,则可将原@Controller注解替换为@RestController
         这样方法上的@ResponseBody就可以省略了

    2.@CrossOrigin  用于@Controller类上
         表示在服务器端支持跨域访问 (跨域访问必须是前后端都支持才能访问成功)


SSM整合的步骤
  1)添加applicationContext_mapper.xml(数据访问层的核心配置文件)和applicationContext_service.xml文件(业务逻辑层的核心配置文件)
      TIP: 1.applicationContext_mapper.xml的配置步骤
                引入外部属性配置文件 (jdbc.properties)
                配置数据源 (这里使用DruidDataSource)
                配置SqlSessionFactoryBean (需要配置DataSource对象、mybatis核心配置文件、需批量起别名的pojo包)
                配置Mapper扫描配置器MapperScannerConfigurer (需批量加载的Mapper.xml文件,同时会生成Mapper接口对应的代理类)
             applicationContext_service.xml配置步骤
                添加包扫描 (扫描service包,以创建ServiceImpl对象,不用扫描mapper包是因为使用mybatis不用写MapperImpl)
                添加事务管理器 (需提供数据源DataSource对象)
                配置事务注解驱动 (需提供事务管理器TransactionManager对象)
                开启aspectj的自动代理 (自动扫描有关AOP注解,否则要手动写 '切点+通知')
           2.applicationContext_service.xml中注册事务管理器的时候会用到DataSource对象,而applicationContext_mapper.xml中已经注册了一个DruidDataSource
             本着'可复用'的原则,应在service中直接引入mapper中的DruidDataSource
             此时如果没有在web.xml中将两者配进应用域<context-param/>,则ref="druidDataSource"会爆红,不过不用担心,将它俩配入应用域后就恢复正常
           3.使用 'applicationContext_' 作为前缀的原因:a.方便辨识,这两个配置文是要配置到web.xml中的应用域ApplicationContext中的,因此使用applicationContext作为前缀头
                                                     b.方便使用通配符一次指定这两个xml文件作为全局配置
                                                         <context-param>
                                                             <param-name>contextConfigLocation</param-name>
                                                             <param-value>classpath:applicationContext_*.xml</param-value>
                                                         </context-param>

  2)添加spirngmvc.xml文件
      添加包扫描 (扫描controller包,以交给Spring管理)
      添加注解驱动 (用于扫描springmvc相关注解)
      // 配置视图解析器 (用于拼接GET/POST请求,如果只使用Ajax请求则无需配置该项)

  3)在web.xml文件中进行以下配置
      添加中文编码过滤器 (CharacterEncodingFilter,并初始化其三个属性)
      注册SpringMVC框架 (DispatcherServlet,并指定其contextConfigLocation属性)
      注册Spring框架监听器 (ContextLoaderListener,目的是启动Web项目时一并启动Spring容器,因为在Web上启动Spring并不能像平时测试那样new ClassPathXmlApplicationContext())
      使用通配符,注册applicationContext_mapper.xml和applicationContext_service.xml (将两个xml文件资源整合起来,相当于引入了两者的第三方spring.xml)

  4)根据'前后端分离接口文档' (见/resources/ready)
      新建实体类user
      新建UserMapper接口 及 UserMapper.xml实现UserMapper接口中所要求功能
      新建UserService接口 及 其实现类UserServiceImpl (会使用UserMapper作为内部对象,可通过@Autowired自动注入)
      新建UserController类承接对应的url (会使用UserService作为内部对象,可通过@Autowired自动注入)

  5)新建测试类ServiceTest,完成对UserService所有功能的测试 (确保Mapper层和Service层功能没有问题)

  6)打开Tomcat,在浏览器地址栏键入对应的url测试UserController (确保Controller层功能没有问题,前后端可以实现基本交互)
      获取数据时
         在还没有搭建View层时对Controller层进行测试直接在地址栏键入 ${pageContext.request.contextPath}/user/selectUserPage?userName=&userSex=
      观察数据时(尤其是观察集合数据)
         建议通过 'F12->Network->对应请求->Preview' 查看

  7)整合前端(这里前端并没有实现update功能,只能测试CRD)
      构建项目(前提是安装了node.js)
        使用命令行进入到当前要运行的vue的项目的目录下,运行以下命令进行项目搭建.
          cd D:\Workspace\SpringMVC\vuedemo01  进入到当前项目的目录下(也可直接在目录下cmd定位)
          下载elementUI的框架   npm i element -ui -S
          打包项目              npm install
          下载跨域访问组件axios  npm install --save vue-axios

      在IDEA中配置npm
          Node interpreter指向node.js安装目录的/nodejs/node (这里为C:/Program Files/nodejs/node)
          package.json指向vuedemo01项目中的package.json文件
          Scripts选择dev

      启动Tomcat后再启动npm,就可以将后端项目部署到前端项目中去了
    TIP: vuedemo01/config/index.js下的target:'http://localhost:8082'和port: 8080 (跨域访问)
         前者表示该项目对应的后端接口
            想要将前后端结合,还要将Tomcat的端口号改为8082 (当然也可以将target:'http://localhost:8082'端口号改为8080,
                                                        此时前后端端口号一致,就不属于跨域访问了)
         后者表示前端的接口,表示前端使用的接口为8080

applicationContext_mapper.xml、applicationContext_service.xml、spirngmvc.xml都是配置在web.xml中的
区别在于: applicationContext_mapper.xml和applicationContext_service.xml配置在应用域ApplicationContext中
         spirngmvc.xml配置在Dispatcher的contextConfigLocation属性中

各个xml文件中需要扫描的包
    mapper: pojo包 (并不扫描mapper包,因为MapperScannerConfigurer会自动生成Mapper接口对应的代理类)
    service: service包
    mvc: controller包

最好保证mapper层、service层、controller层的方法名、返回值保持一致,增加可读性,一般只是在每一层上为低层的方法添加一些功能后调用低层方法



关于pom.xml文件的改动
    a.在<properties/>中集中指定版本,在<dependencies/>中通过EL表达式引入
         <!--集中定义依赖版本号-->
         <properties>
           <spring.version>5.3.18</spring.version>
         </properties>

         <dependencies>
            <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-context</artifactId>
              <version>${spring.version}</version>
            </dependency>
            <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-beans</artifactId>
              <version>${spring.version}</version>
            </dependency>
            <!--spring的单元测试依赖-->
            <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-test</artifactId>
              <version>5.3.27</version>
            </dependency>
          </dependencies>

    b.可将原<properties/>中的版本编码指定放到<build/>标签下的<plugins/>标签中
          指定 1.JDK编译的版本  2.项目部署到客户端的使用的JDK版本  3.编码格式
          <properties>
              <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
              <maven.compiler.source>17</maven.compiler.source>
              <maven.compiler.target>17</maven.compiler.target>
          </properties>

          将以上指定放入<build/>标签下的<plugins/>标签中(与<resources/>同级,<resources/>是最开始用于指定资源路径的标签,放在<build/>标签下)
          <build>
             <plugins>
                <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-compiler-plugin</artifactId>
                   <configuration>
                      <source>17</source>
                      <target>17</target>
                      <encoding>UTF-8</encoding>
                   </configuration>
                </plugin>
             </plugins>

             <resources>
                <resource>...</resource>
                <resource>...</resource>
             </resources>
          </build>


解析Vue项目
    1.文件
        README.md 	  项目的说明文档
        package.json  存放项目所用的Vue版本(见"dependencies":)、启动方式(见"scripts":)等信息
    2.包
        build 	项目构建(webpack)相关代码
        config 	配置目录,包括端口号等。初学可以使用默认的
        node_modules 	npm 加载的项目依赖模块 (类似maven中引入的jar包,不用关心)

       *src   开发目录,基本上要做的事情都在这个目录里
        里面包含了几个目录及文件：
            assets: 放置一些图片,如logo等
            components: 目录里面存放组件文件,可以不用(原因见下面的App.vue)
            App.vue: 项目入口文件,可以直接将组件写这里,而不使用 components 目录
            router: 一般存放index.js,该文件会引用components目录下的.vue文件,将其放入App.vue中作为主界面(主界面=App.vue+components组件)
            main.js: 项目的核心文件


    关于App.vue
      <div id="app">
        <router-view/>      //表示采用'/'
         <router-link to="/user">UserHome</router-link> |       //表示采用'/user'
      </div>    //总的来说采用 '/' 和 '/user'

    关于index.js
      routes: [{
              path: '/',
              name: 'HelloWorld',
              component: HelloWorld
          },        //表示将HelloWorld.vue放到 '/' 上
          {
              path: '/user',
              name: 'UserHome',
              component: UserHome
          }         //表示将UserHome.vue放到 '/user' 上
      ]

      也就是说最后将 HelloWorld.vue 和 UserHome.vue 放到App.vue上


