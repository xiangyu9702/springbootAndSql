# 基于springboot与swaggerUI的api接口

基于springboot与swaggerUI的api接口

swagger-ui采用了xiaoymin的swagger-ui主题

https://github.com/xiaoymin/Swagger-Bootstrap-UI
使用SpringBoot集成SwaggerUi与MySql实现快速API

1、开发环境搭建
开发环境：
Java 1.8
spring-2.0.0.RELEASE
mysql-8.0.13
Maven 3.6.0


2、使用Maven构建项目
访问：http://start.spring.io/，通过SPRING INITIALIZR工具产生基础项目

解压项目包，并用IDE以Maven项目导入，菜单中选择File–>New–>Project from Existing Sources，选择解压后的项目文件夹，点击OK，点击Import project from external model并选择Maven，点击Next到底为止。
通过上面步骤完成了基础项目的创建，如上图所示，Spring Boot的基础结构共三个文件
src/main/java下的程序入口：Application
src/main/resources下的配置文件：application.properties
src/test/下的测试入口：ApplicationTests

3、引入依赖

4、创建Swagger2配置类
在Application.java同级创建Swagger2的配置类Swagger2。}
通过@Configuration注解，让Spring来加载该类配置。再通过@EnableSwagger2注解来启用Swagger2。通过createRestApi函数创建Docket的Bean之后，apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）

5、配置数据库信息
创建Database类，储存JDBC 驱动名及数据库 URL和数据库的用户名与密码
package com.example.web;
/*
数据库信息
 */


6、创建实体类
本例共5个实体类:Institute（学院），Major（专业），	Course（课程），Student（学生），Teacher（教师）。

7、数据库方法创建
每个实体类有四种方法，getMap：从数据库获取信息delete：从数据库删除信息，save：保存新的信息，update:更新信息。
以下仅给出Institute（学院）的数据库方法及其他实体类的Sql语句。

8、Controller配置
Controller包下共有5个类：CourseController，MajorController，InstituteController，StudentController，TeacherController。

9、访问页面
本例在pom.xml 中引入了swagger-ui-layer 的依赖（来源：https://github.com/xiao
ymin/Swagger-Bootstrap-UI）。
完成上述代码，启动Spring Boot程序，访问：http://localhost:8080/doc.html。就能看到下图所展示的RESTful API的页面。

