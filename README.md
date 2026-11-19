# demo2

基于 Spring Boot 的电商后端示例，提供用户认证、商品与购物车管理、订单查询和 Stripe 结账接口，并使用 MySQL 持久化数据。

## 功能

- **认证与授权**：用户注册、邮箱密码登录、JWT 访问令牌和刷新令牌；接口使用无状态 Spring Security，管理员接口要求 `ADMIN` 角色。
- **用户管理**：用户列表（支持按姓名或邮箱排序）、详情、注册、更新、删除和密码修改。
- **商品管理**：查询全部商品或按分类筛选、查询单个商品，以及创建、更新和删除商品。
- **购物车**：创建和查看购物车、添加或更新商品、移除商品、清空购物车。
- **结账与订单**：基于购物车发起 Stripe 结账，查询订单列表和订单详情。
- **Stripe Webhook**：验证 Stripe 签名并接收支付事件。成功或失败事件对应的订单状态更新尚未实现。
- **其他**：Thymeleaf 首页、`/hello` 示例接口、管理员示例接口、统一异常处理、请求日志过滤器和 Flyway 数据库迁移。
- **API 文档**：集成 Springdoc OpenAPI / Swagger UI。

## API 概览

除明确开放的接口外，请求需要通过 JWT 认证。`/carts/**` 和 Stripe Webhook 当前配置为公开访问；`/admin/**` 仅限管理员。

| 路径 | 方法 | 说明 |
| --- | --- | --- |
| `/auth/login` | POST | 登录并取得访问令牌 |
| `/auth/refresh` | POST | 使用刷新令牌获取新的访问令牌 |
| `/auth/me` | GET | 获取当前登录用户 |
| `/users` | GET, POST | 查询用户、注册用户 |
| `/users/{id}` | GET, PUT, DELETE | 查询、更新或删除用户 |
| `/users/{id}/change-password` | POST | 修改用户密码 |
| `/products` | GET, POST | 查询或创建商品；查询支持 `categoryId` |
| `/products/{id}` | GET, PUT, DELETE | 查询、更新或删除商品 |
| `/carts` | POST | 创建购物车 |
| `/carts/{cartId}` | GET | 查询购物车 |
| `/carts/{cartId}/items` | POST, DELETE | 添加商品或清空购物车 |
| `/carts/{cartId}/items/{productId}` | PUT, DELETE | 更新或移除购物车商品 |
| `/checkout` | POST | 创建结账会话 |
| `/checkout/webhook` | POST | 接收 Stripe 支付事件 |
| `/orders` | GET | 查询订单列表 |
| `/orders/{orderId}` | GET | 查询订单详情 |
| `/admin/hello` | GET | 管理员示例接口 |
| `/hello` | GET | 示例问候接口 |
| `/` | GET | Thymeleaf 首页 |

Swagger UI 默认地址为 `http://localhost:8080/swagger-ui.html`，OpenAPI 描述默认位于 `http://localhost:8080/v3/api-docs`。

## 技术栈与依赖

- **运行环境**：Java 25、Maven Wrapper、Spring Boot 4.0.3
- **Spring Boot 组件**：Web MVC、Data JPA、Validation、Security、Thymeleaf、Flyway
- **数据库**：MySQL Connector/J；Flyway MySQL 数据库支持
- **认证与安全**：Spring Security、JJWT（API、实现和 Jackson 集成）、BCrypt
- **支付**：Stripe Java SDK
- **对象映射**：MapStruct
- **辅助与文档**：Lombok、Spring Boot dotenv、Springdoc OpenAPI UI、Thymeleaf Spring Security extras
- **测试依赖**：Spring Boot Web MVC Test、Spring Security Test

### Maven 插件

- `spring-boot-maven-plugin`：运行及打包 Spring Boot 应用。
- `flyway-maven-plugin`：执行 Flyway 数据库迁移。

## 环境配置

需要安装 JDK 25，并准备可访问的 MySQL 实例。应用默认连接 `localhost:3306` 上名为 `demo2` 的数据库；连接信息可在 `src/main/resources/application.YAML` 中调整。Flyway 的 Maven 插件也在 `pom.xml` 中单独配置了数据库连接信息。

在项目根目录创建 `.env` 文件，并设置以下变量：

```dotenv
JWT_SECRET=<用于签署 JWT 的密钥>
STRIPE_SECRET_KEY=<Stripe API 密钥>
STRIPE_WEBHOOK_SECRET_KEY=<Stripe Webhook 签名密钥>
```

`.env.example` 提供了变量模板。不要将真实密钥提交到版本控制。部署前请根据目标环境检查并修改应用和 Maven 插件中的数据库连接配置。

## 运行

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

macOS / Linux:

```bash
./mvnw spring-boot:run
```

应用启动时由 Flyway 执行 `src/main/resources/db/migration` 中的数据库迁移。默认服务端口为 Spring Boot 默认端口 `8080`。

## 测试与打包

Windows:

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

macOS / Linux:

```bash
./mvnw test
./mvnw package
```

## 项目结构

```text
src/main/java/com/example/demo/
├── config/        # JWT、Spring Security 和 Stripe 配置
├── controllers/   # HTTP API 控制器
├── dtos/          # 请求与响应对象
├── entities/      # JPA 实体
├── filters/       # JWT 认证与请求日志过滤器
├── mappers/       # DTO 与实体映射
├── repositories/  # Spring Data 仓库
└── services/      # 业务逻辑
src/main/resources/
├── db/migration/  # Flyway SQL 迁移
└── templates/     # Thymeleaf 页面
```

## 维护说明

新增、删除或调整 API、功能、运行要求、依赖或 Maven 插件时，请同步更新本 README 中相应的功能列表、API 概览、依赖或运行说明；支付回调等尚未完成的能力也应明确标注状态。
