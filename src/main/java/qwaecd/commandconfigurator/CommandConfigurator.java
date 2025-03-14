package qwaecd.commandconfigurator;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import qwaecd.commandconfigurator.config.CommonConfig;

@Mod(CommandConfigurator.MODID)
public class CommandConfigurator
{
    public static final String MODID = "commandconfigurator";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CommandConfigurator(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//        LOGGER.info("Command Configurator!");
//    }
}
