package com.behave;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HelloBehaveTestCase
{
    @Test
    public void name() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HelloBehave.print(new PrintStream(out));

        String s = out.toString();

        Assert.assertEquals("Hello, Behave!", s);
    }
}
