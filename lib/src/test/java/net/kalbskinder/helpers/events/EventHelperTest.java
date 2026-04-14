package net.kalbskinder.helpers.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class EventHelperTest {
    private ServerMock server;
    private EventHelper eventHelper;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        PluginMock plugin = MockBukkit.createMockPlugin();
        eventHelper = new EventHelper(plugin);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void subscribeRoutesTypedEventToLambda() {
        AtomicReference<String> payload = new AtomicReference<>();

        eventHelper.subscribe(TestEvent.class, event -> payload.set(event.getPayload()));

        server.getPluginManager().callEvent(new TestEvent("hello"));

        Assert.assertEquals("hello", payload.get());
    }

    @Test
    public void subscribeWithIgnoreCancelledSkipsCancelledEvent() {
        AtomicBoolean handled = new AtomicBoolean(false);

        eventHelper.subscribe(
                TestCancellableEvent.class,
                EventPriority.NORMAL,
                true,
                event -> handled.set(true)
        );

        TestCancellableEvent event = new TestCancellableEvent();
        event.setCancelled(true);
        server.getPluginManager().callEvent(event);

        Assert.assertFalse(handled.get());
    }

    private static final class TestEvent extends Event {
        private static final HandlerList HANDLERS = new HandlerList();
        private final String payload;

        private TestEvent(String payload) {
            this.payload = payload;
        }

        public String getPayload() {
            return payload;
        }

        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }

        @SuppressWarnings("unused")
        public static HandlerList getHandlerList() {
            return HANDLERS;
        }
    }

    private static final class TestCancellableEvent extends Event implements Cancellable {
        private static final HandlerList HANDLERS = new HandlerList();
        private boolean cancelled;

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }

        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }

        @SuppressWarnings("unused")
        public static HandlerList getHandlerList() {
            return HANDLERS;
        }
    }
}


