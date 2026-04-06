package net.kalbskinder.helpers;

import net.kalbskinder.helpers.regions.RegionManager;
import org.bukkit.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

public class InitHelpersTest {
    // Simulate server start and initialize all helper classes

    private Server server;
    private PluginMock plugin;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void initializeHelpers() {
        Assert.assertNotNull(server);
        Assert.assertNotNull(plugin);

        RegionManager regionManager = new RegionManager();

        Helpers helpers = new Helpers(regionManager);
        helpers.initialize();

        Assert.assertNotNull(Helpers.messageHelper);
        Assert.assertNotNull(Helpers.itemHelper);
        Assert.assertNotNull(Helpers.miniMessageHelper);
        Assert.assertNotNull(Helpers.locationHelper);
        Assert.assertNotNull(Helpers.soundHelper);
        Assert.assertNotNull(Helpers.regionHelper);
        Assert.assertNotNull(Helpers.teleportHelper);
        Assert.assertNotNull(Helpers.titleHelper);
        Assert.assertNotNull(Helpers.playerItemHelper);
    }

}
