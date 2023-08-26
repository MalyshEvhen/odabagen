package com.github.malyshevhen;

import com.github.malyshevhen.build.BuildCommand;
import com.github.malyshevhen.scan.ScanCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

@Command(name = "odabagen", description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {ScanCommand.class, BuildCommand.class})
public class OdabagenCommand implements Runnable {

    @Mixin
    GlobalOptions globalOptions;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(OdabagenCommand.class, args);
    }

    public void run() {
        /* business logic here */
        if (globalOptions.isVerbose()) {
            System.out.println("Hi!");
        }
    }
}
