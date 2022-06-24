# Hosppy 练习版

医院挂号系统，微服务和云原生架构，基于 Spring Boot 和 Kubernetes 技术栈

## 项目目的

微服务和云原生架构是目前互联网行业的技术热点，相关资料文档很多，但是缺乏系统的学习，为此，通过参考开源项目掌握云原生和微服务开发实践，并且可以一键部署到 Kubernetes 容器云环境。

## 快速开始

### 环境变量

您可以通过环境变量配置 Hosppy 的一些设置：

（默认值以粗体显示）

- `SPRING_PROFILES_ACTIVE`：**test**：应用程序运行模式。"dev"，"test"，"prod"
- `SERVER_PORT`：**80**：应用程序运行时开放端口
- `SIGNING_SECRET`："<empty>"：用户密码加密密钥
- `WEB_DOMAIN`："<empty>"：网站域名地址
- `ACCOUNT_SERVICE_ENDPOINT`：**http://account-service**：账号服务访问端点
- `EMAIL_SERVICE_ENDPOINT`：**http://email-service**：邮件服务访问端点
- `ACCOUNT_DATASOURCE_URL`："<empty>"：账号服务数据库地址
- `ACCOUNT_DATASOURCE_USERNAME`：**root**：账号服务数据库用户名
- `ACCOUNT_DATASOURCE_PASSWORD`：**root**：账号服务数据库密码
- `ALIYUN_ACCESS_KEY`："<empty>"：阿里云账号 ID
- `ALIYUN_ACCESS_SECRET`："<empty>"：阿里云账号密钥
- `MAIL_FROM`："<empty>"：邮件地址，在阿里云配置
- `MAIL_FROM_NAME`："<empty>"：邮件发件名，在阿里云配置

### 在开发环境中运行本应用

```shell
$ mvn clean package
$ docker-compose up --build
```

## 项目技术栈

- Spring Boot
- Spring Data JPA
- Spring MVC + Thymeleaf
- MySQL
- Docker Compose
- Kubernetes

## 注意

本项目不是一个完整的项目，由于没有实现前端页面，具体的业务逻辑，以及网关鉴权等功能，所以并不能真正的使用，这只是做为我学习云原生与微服务架构开发理念的练习项目。