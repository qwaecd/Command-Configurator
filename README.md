## Command Configurator

一个基于Forge 1.20.1的服务器命令拦截模组，通过配置文件实现细粒度的命令管理

## 功能特性

- 🛡️ 按层级禁用特定命令
- ⚙️ 支持通配符禁用整个命令树
- 👮 可选OP权限绕过机制
- 📁 基于TOML的易读配置文件
- 支持添加玩家白名单

## 配置文件说明

配置文件路径：`config/commandconfigurator-common.toml`

### 配置参数

```toml
[general]
   #enable or disable command limit
   #是否启用命令限制功能
   "command limit enabled" = true

   #是否允许op管理员绕过限制
   "allow op root" = true

   #分层格式的禁用命令列表
    disabledCommands = ["give.*", "kill.@e"]

    #This is a whitelist, used to store whitelist player names.
    #这是一个白名单列表，用于存储白名单玩家名称。
    #e.g. ["qwaecd"]
    whiteListPlayerName = []
```

### 禁用命令格式指南

使用 **点分隔符** 表示命令层级结构：
- `"give.player"` → 禁用 `/give <player>`
- `"msg.send"` → 禁用 `/msg send`
- `"give"` → 禁用所有`/give`及其子命令（等效于`"give.*"`）

示例配置：
```toml
disabledCommands = [
    "tp",                # 完全禁用/tp命令
    "give.player",       # 禁用/give
    "msg.send"           # 禁用/msg命令
]
```

## 使用方法

1. 将mod文件放入`mods`文件夹
2. 启动服务器生成配置文件
3. 编辑`commandconfigurator-common.toml`

配置文件修改后重启服务器生效


## 注意事项

-  命令匹配区分大小写（配置会自动转小写）
-  使用星号`*`可显式声明通配符（如`["give.*"]`等效于`["give"]`）
