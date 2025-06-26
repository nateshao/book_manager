# 图书管理系统 Book Manager

基于 Spring Boot 3 + Vue 3 + Element Plus 的前后端分离图书管理系统，支持图书、用户、借阅、分类等核心业务，支持 Docker 一键部署。

## 技术栈
- 前端：Vue 3, Vite, TypeScript, Element Plus, Pinia, Vue Router, Axios
- 后端：Spring Boot 3, Spring Data JPA, Spring Security, Lombok, MySQL 8
- 工程化：Maven, Docker, docker-compose

## 目录结构
```
book_manager/
├── backend/         # Spring Boot 后端
├── frontend/        # Vue 3 前端
├── docker/          # Docker相关文件
│   ├── backend.Dockerfile
│   ├── frontend.Dockerfile
│   └── nginx.conf
│   └── docker-compose.yml
└── README.md
```

## 启动方式
### 1. 本地开发
- 后端：
  1. 配置 MySQL，修改 backend/src/main/resources/application.yml
  2. 进入 backend 目录，运行 `mvn spring-boot:run`
- 前端：
  1. 进入 frontend 目录，运行 `npm install && npm run dev`

### 2. Docker 一键部署
1. 进入 docker 目录
2. 运行 `docker-compose up --build`
3. 前端访问：http://localhost/
4. 后端接口：http://localhost:8080/
5. 数据库：localhost:3306，用户名 root，密码 root

## 功能模块
- 用户注册、登录、权限管理（管理员/普通用户）
- 图书增删改查、分类管理
- 借阅、还书、借阅记录查询
- 统计与报表（可扩展）

## 默认端口
- 前端：80
- 后端：8080
- 数据库：3306

## 其他
- 如需扩展功能或有疑问，欢迎 issue 或联系作者。 