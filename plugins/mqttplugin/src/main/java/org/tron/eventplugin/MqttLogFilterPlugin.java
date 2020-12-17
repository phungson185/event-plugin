package org.tron.eventplugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class MqttLogFilterPlugin extends Plugin {

    public MqttLogFilterPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        MessageSenderImpl.getInstance().close();
    }
}
