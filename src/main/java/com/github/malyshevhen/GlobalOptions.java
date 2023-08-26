package com.github.malyshevhen;

import picocli.CommandLine.Option;

public class GlobalOptions {

    @Option(names = {"-v", "--verbose"})
    private boolean verbose;

    public boolean isVerbose() {
        return verbose;
    }
}
