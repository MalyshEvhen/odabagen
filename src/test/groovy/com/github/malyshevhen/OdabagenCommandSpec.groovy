package com.github.malyshevhen

import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class OdabagenCommandSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)

    void "test odabagen scan command"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ['scan', '-v'] as String[]
        PicocliRunner.run(OdabagenCommand, ctx, args)
        out.println out.toString()

        expect:
        baos.toString().contains('Scan command run in verbose mode...')
    }
}

