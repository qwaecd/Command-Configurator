package qwaecd.commandconfigurator.config;

import net.minecraftforge.common.ForgeConfigSpec;
import qwaecd.commandconfigurator.CommandConfigurator;

import java.util.*;

public class CommonConfig {
    public static ForgeConfigSpec SPEC;

    private static ForgeConfigSpec.BooleanValue commandLimit;
    private static ForgeConfigSpec.BooleanValue opLimit;
    private static Map<String, List<String>> DISABLED_COMMAND_TREE;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> DISABLED_COMMANDS;


    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(BUILDER);
        SPEC = BUILDER.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder builder){
        builder.push("general");
        //[General]:
            commandLimit = builder
                        .comment("enable or disable command limit")
                        .comment("是否启用命令限制功能")
                        .define("command limit enabled",true);
            opLimit = builder
                    .comment("是否允许服务器管理员绕过检查")
                    .define("allow op root",true);
            DISABLED_COMMANDS = builder
                    .comment("Disabled commands in hierarchical format (e.g. [\"give.player\", \"msg.send\"])")
                    .comment("分层格式的禁用命令列表(例如 [\"give.player\", \"msg.send\"])")
                    .defineListAllowEmpty("disabledCommands",
                            List.of(),
                            obj -> obj instanceof String);
        builder.pop();
    }

    public static boolean enableLimit(){
        return commandLimit.get();
    }

    public static List<String> getDisabledCommands() {
        return (List<String>) DISABLED_COMMANDS.get();
    }

    public static void disabledCommandTreeBuild() {
        CommandConfigurator.LOGGER.debug("disabledCommandTreeBuild 被调用！");
        DISABLED_COMMAND_TREE = new HashMap<>();
        getDisabledCommands().forEach(cmd -> {
            String[] parts = cmd.toLowerCase().split("\\.");
            if (parts.length > 0) {
                String root = parts[0];
                List<String> subCommands = DISABLED_COMMAND_TREE
                        .computeIfAbsent(root, k -> new ArrayList<>());
                if (parts.length == 1) {
                    subCommands.add("*"); // 添加通配符
                } else {
                    subCommands.add(String.join(".", Arrays.copyOfRange(parts, 1, parts.length)));
                }
            }
        });
    }

    public static boolean isCommandBlocked(String[] commandParts) {
        if (DISABLED_COMMAND_TREE == null) return false;
        if (commandParts.length == 0) return false;

        String root = commandParts[0].toLowerCase();
        if (!DISABLED_COMMAND_TREE.containsKey(root)) return false;

        List<String> disabledSubs = DISABLED_COMMAND_TREE.get(root);
        String executedPath = String.join(".", Arrays.copyOfRange(commandParts, 1, commandParts.length));

        return disabledSubs.stream().anyMatch(pattern ->
                "*".equals(pattern) || executedPath.startsWith(pattern)
        );
    }

    public static boolean getOpLimit() {
        return opLimit.get();
    }
}