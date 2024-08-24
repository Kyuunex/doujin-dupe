package moe.kyuunex.doujin_dupe;

import moe.kyuunex.doujin_dupe.modules.AutoDump;
import moe.kyuunex.doujin_dupe.modules.DoujinDupe;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class DoujinDupeAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Doujin Dupe");

    @Override
    public void onInitialize() {
        LOG.info("Initializing the Doujin Dupe Meteor Addon");

        // Modules
        Modules.get().add(new AutoDump());
        Modules.get().add(new DoujinDupe());

    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "moe.kyuunex.doujin_dupe";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("Kyuunex", "doujin-dupe");
    }
}
