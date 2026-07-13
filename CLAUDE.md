# 苍穹外卖 (CangQiong WaiMai)

## 项目概述

苍穹外卖是一个外卖点餐系统，采用前后端分离架构。

## 项目结构

```
cangqionwaimai/
├── nginx-1.20.2/          # 前端 — 已打包好的静态资源 (Nginx)
│   └── html/sky/          # 前端页面 (HTML/CSS/JS, SPA)
├── sky-take-out/          # 后端 — Java Maven 多模块项目 (开发中)
│   ├── sky-common/        # 公共模块 (工具类、常量等)
│   ├── sky-pojo/          # 数据模型模块 (实体类、DTO、VO)
│   └── sky-server/        # 服务端模块 (Controller、Service、Mapper)
└── sql/                   # 数据库脚本
```

## 技术栈

### 后端 (sky-take-out)
- **构建工具**: Maven
- **模块结构**:
  - `sky-common` — 公共工具类与通用配置
  - `sky-pojo` — 实体类、DTO、VO 等数据对象
  - `sky-server` — 主服务模块 (Spring Boot 应用入口)
- 源码目录: `src/main/java/`, `src/main/resources/`
- 测试目录: `src/test/java/`

### 数据库
- SQL 脚本存放在项目根目录的 `sql/` 文件夹中

## 启动方式

### 前端
```bash
cd nginx-1.20.2
nginx.exe
```
访问 http://localhost 即可。

### 后端
```bash
cd sky-take-out
mvn clean package -DskipTests
java -jar sky-server/target/sky-server-*.jar
```

## 开发说明

- 前端已打包完成，通常无需修改
- 后端 `sky-take-out` 为开发中的 Maven 多模块项目，各模块职责划分如上
- 数据库变更脚本统一放入 `sql/` 目录管理
