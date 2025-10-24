# JLU-mart 校园外卖平台

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/java-1.8%2B-blue.svg)
![Spring Boot](https://img.shields.io/badge/spring--boot-2.x-blue.svg)

JLU-mart 是一个基于Java的校园外卖平台，专为校园环境设计，提供用户、配送员和管理员三种角色的完整解决方案。

## 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术架构](#技术架构)
- [系统模块](#系统模块)
- [开发环境](#开发环境)
- [部署说明](#部署说明)
- [数据库设计](#数据库设计)
- [API文档](#api文档)
- [项目结构](#项目结构)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

## 项目简介

JLU-mart 是一个功能完善的校园外卖平台，旨在为校园用户提供便捷的在线订餐服务。平台支持用户浏览商品、下单、支付、查看订单状态等功能，同时为配送员和管理员提供相应的管理系统。

## 功能特性

### 用户端功能
- 用户注册/登录（JWT Token认证）
- 个人信息管理
- 收货地址管理（集成百度地图API）
- 商品浏览与搜索
- 购物车管理
- 商品收藏
- 订单创建与支付
- 订单状态跟踪
- 账户余额充值

### 配送员端功能
- 配送员登录
- 个人信息管理
- 订单接收
- 配送状态更新

### 管理员端功能
- 员工管理
- 商品分类管理
- 商品信息管理
- 订单管理
- 配送员管理
- 数据统计与分析

## 技术架构

### 后端技术栈
- **核心框架**: Spring Boot 2.x
- **持久层框架**: MyBatis
- **数据库**: Oracle
- **缓存**: Redis
- **安全认证**: JWT (JSON Web Tokens)
- **API文档**: Swagger (knife4j)
- **对象存储**: 阿里云 OSS
- **地图服务**: 百度地图 API
- **构建工具**: Maven

### 前端技术栈
- **网页端**: Vue.js + Element UI

## 系统模块

### 1. 用户模块 (User)
- 用户注册/登录
- 个人信息维护
- 密码修改
- 地址管理
- 余额充值

### 2. 商品模块 (Product)
- 商品分类展示
- 商品详情查看
- 商品搜索
- 商品收藏

### 3. 购物车模块 (Shopping Cart)
- 添加商品到购物车
- 修改购物车商品数量
- 删除购物车商品

### 4. 订单模块 (Order)
- 创建订单
- 订单支付
- 订单状态管理
- 订单取消

### 5. 配送模块 (Courier)
- 配送员接单
- 配送状态更新
- 配送完成确认

### 6. 管理模块 (Admin)
- 员工管理
- 商品管理
- 分类管理
- 订单管理
- 数据统计

## 开发环境

- **JDK**: 1.8 或更高版本
- **Maven**: 3.6 或更高版本
- **数据库**: Oracle 11g 或更高版本
- **缓存**: Redis 3.0 或更高版本
- **IDE**: IntelliJ IDEA 或 Eclipse

## 部署说明

### 环境准备
1. 安装 JDK 1.8+
2. 安装 Maven 3.6+
3. 安装 Oracle 数据库
4. 安装 Redis
5. 注册阿里云 OSS 账号
6. 注册百度地图 API 账号

### 配置文件
在 `src/main/resources/application-dev.yml` 中配置以下信息：

```yaml
jlumart:
  datasource:
    driver-class-name: oracle.jdbc.OracleDriver
    host: 192.168.80.10
    port: 1521
    database: orcl
    username: jlumartadmin
    password: password
  alioss:
    endpoint: your-endpoint
    access-key-id: your-access-key-id
    access-key-secret: your-access-key-secret
    bucket-name: your-bucket-name
  redis:
    host: 127.0.0.1
    port: 6379
    password: 123456
    database: 0
  jwt:
    admin-secret-key: your-secret-key
    user-secret-key: your-secret-key
    courier-secret-key: your-secret-key
  map:
    address: your-address
    longitude: your-longitude
    latitude: your-latitude
    ak: your-ak
```

### 启动项目
```bash
# 克隆项目
git clone https://github.com/your-username/JLU-mart.git

# 进入项目目录
cd JLU-mart

# 编译项目
mvn clean install

# 启动服务端
cd mart-server
mvn spring-boot:run
```

## 数据库设计

主要数据表包括：
- 用户表 (User)
- 配送员表 (Courier)
- 管理员表 (Employee)
- 商品分类表 (Category)
- 商品表 (Product)
- 购物车表 (ShoppingCart)
- 订单表 (ShoppingOrder)
- 充值订单表 (PaymentOrder)
- 地址簿表 (AddressBook)
- 收藏表 (Favorite)

## API文档

项目集成了 Swagger API 文档，启动项目后访问:
```
http://localhost:8080/doc.html
```

## 项目结构

```
JLU-mart/
├── mart-common/          # 公共模块
│   ├── constant/         # 常量定义
│   ├── context/          # 上下文工具
│   ├── exception/        # 自定义异常
│   ├── properties/       # 配置属性
│   ├── result/           # 统一返回结果
│   └── utils/            # 工具类
├── mart-pojo/            # 数据传输对象
│   ├── dto/              # 数据传输对象
│   ├── entity/           # 实体类
│   └── vo/               # 视图对象
└── mart-server/          # 服务端
    ├── config/           # 配置类
    ├── controller/       # 控制器
    ├── interceptor/      # 拦截器
    ├── mapper/           # 数据访问层
    ├── service/          # 业务逻辑层
    ├── task/             # 定时任务
    └── resources/        # 资源文件
```

## 贡献指南

欢迎任何形式的贡献！如果你有任何建议或发现了bug，请提交 issue 或 pull request。

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证，详情请见 [LICENSE](LICENSE) 文件。