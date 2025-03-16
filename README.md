## Command Configurator

一个基于Forge 1.20.1的服务器命令拦截模组，通过配置文件实现细粒度的命令管理

## 功能特性

- 🛡️ 按层级禁用特定命令
- ⚙️ 支持通配符禁用整个命令树
- 👮 可选OP权限绕过机制
- 📁 基于TOML的易读配置文件

## 配置文件说明

配置文件路径：`config/commandconfigurator-common.toml`

### 配置参数

```toml
[general]
   #enable or disable command limit
   #是否启用命令限制功能
   "command limit enabled" = true

   #是否允许OP管理员绕过限制
   "allow op root" = true

   #分层格式的禁用命令列表
    disabledCommands = []
```

### 禁用命令格式指南

使用 **点分隔符** 表示命令层级结构：
- `"give.player"` → 禁用 `/give <player>`
- `"msg.send"` → 禁用 `/msg send`
- `"give"` → 禁用所有`/give`及其子命令（等效于`"give.*"`）

示例配置：
```toml
disabledCommands = [
    "teleport",          # 完全禁用/teleport命令
    "give.player",       # 禁用物品给予功能
    "msg.send",          # 禁用私信发送
    "teammsg.execute"    # 禁用团队消息执行
]
```

## 安装方法

1. 将mod文件放入`mods`文件夹
2. 启动服务器生成配置文件
3. 编辑`commandconfigurator-common.toml`
4. 重载配置或重启服务器生效

## 工作原理

1. **命令解析**：将输入命令拆分为层级结构（如`/give player` → `["give", "player"]`）
2. **规则匹配**：
   - 完全匹配：`teleport` → 拦截`/teleport`
   - 部分匹配：`give.player` → 拦截`/give <player>`
   - 通配符匹配：`give` → 拦截所有`/give`子命令
3. **权限检查**：当`allowOpRoot=true`时，OP玩家可绕过限制

## 注意事项

- [x] 配置变更后需执行`/reload`或重启服务器
- [x] 命令匹配区分大小写（配置会自动转小写）
- [x] 使用星号`*`可显式声明通配符（如`["give.*"]`等效于`["give"]`）
- [ ] 客户端Tab无法补全被禁用命令
