package com.behave;

import java.io.PrintStream;

/**
 * A simple hello world
 */
public class HelloBehave
{
    public static void main(String[] arg)
    {
        print(System.out);
    }

    static void print(PrintStream ps)
    {
        ps.println("Hello, Behave!");
    }
}
