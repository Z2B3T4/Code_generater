package com.yupi.cli;

// 这个类就相当于遥控器，用来操作那些命令的

import com.yupi.cli.command.ConfigCommand;
import com.yupi.cli.command.GenerateCommand;
import com.yupi.cli.command.ListCommand;
import picocli.CommandLine;

@CommandLine.Command(name = "yuzi",mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable{

    private CommandLine commandLine;
    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        System.out.println("输入 --help获取帮助");
    }

    public Integer doExecute(String[] args){
        return commandLine.execute(args);
    }
}
