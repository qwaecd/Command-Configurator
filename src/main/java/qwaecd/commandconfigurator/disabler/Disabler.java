package qwaecd.commandconfigurator.disabler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import qwaecd.commandconfigurator.config.CommonConfig;

import static qwaecd.commandconfigurator.CommandConfigurator.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Disabler {

    @SubscribeEvent
    public static void onModConfigLoadingEvent(AddReloadListenerEvent event) {
        CommonConfig.disabledCommandTreeBuild();

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    private static class CommandHandler {
        @SubscribeEvent
        public static void onCommand(CommandEvent event){
            if(!CommonConfig.enableLimit()) return;
            var source = event.getParseResults().getContext().getSource();
            //找不到玩家结束判断
            Player player = source.getPlayer();
            if(player == null) return;
            if(player.hasPermissions(4) && CommonConfig.getOpLimit()) return;

            String command = event.getParseResults().getReader().getString();
            if(command.startsWith("/")) command = command.substring(1);
            String[] cmdTree = command.split(" ");

            if(CommonConfig.isCommandBlocked(cmdTree)){
                player.sendSystemMessage(Component.literal("该命令已被禁用"));
                event.setCanceled(true);
            }

        }
    }

}
