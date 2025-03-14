package qwaecd.commandconfigurator.config;

import net.minecraftforge.common.ForgeConfigSpec;
import java.util.List;

public class CommonConfig {
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.BooleanValue commandLimit;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DISABLED_COMMANDS;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(BUILDER);
        SPEC = BUILDER.build();
    }
    private static void setupConfig(ForgeConfigSpec.Builder builder){
        builder.push("General");
        //[General]:
            commandLimit = builder
                        .comment().translation("config.commandlimit")
                        .define("command limit enabled",true);
        DISABLED_COMMANDS = builder
                .comment().translation("config.disabledcommands")
                .defineListAllowEmpty("disabledCommands",
                        List.of(), // 默认空列表
                        obj -> obj instanceof String); // 验证元素类型
        builder.pop();
    }
    public static List<String> getDisabledCommands() {
        return (List<String>) DISABLED_COMMANDS.get();
    }
}