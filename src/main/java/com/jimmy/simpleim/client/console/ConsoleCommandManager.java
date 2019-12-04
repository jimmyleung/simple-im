package com.jimmy.simpleim.client.console;

import com.jimmy.simpleim.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private Map<Byte, ConsoleCommand> commandMap;

    public ConsoleCommandManager() {
            this.commandMap = new HashMap<>();
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        if (!SessionUtil.islLogin(channel)) {
            return;
        }
        System.out.println("请输入指令");
        String command = scanner.nextLine();
        ConsoleCommand consoleCommand = commandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("指令有误，请重新输入");
        }
    }
}
