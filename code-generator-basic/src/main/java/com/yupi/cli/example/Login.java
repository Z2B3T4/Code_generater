package com.yupi.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "User name")
    String user;

    @Option(names = {"-p", "--password"},
            description = "Passphrase",
            interactive = true,
            arity = "0..1"
    )
    String password;

    @Option(names = {"-cp","--checkPassword"},
            description = "check",
            prompt = "请确认密码" ,
            interactive = true
    )
    String checkPassword;
    public Integer call() throws Exception {
        System.out.println("password = " + password);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new Login()).execute("-u", "user123", "-p","xxx","-cp");
    }
}
