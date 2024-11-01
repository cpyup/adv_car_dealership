package com.pluralsight.ui;


/**
 * A utility class that contains ANSI color codes for console text formatting.
 * This class provides static constants for various color codes used to
 * format console output, including header colors, table row colors, and
 * separator colors. The purpose of these codes is to enhance the visual
 * presentation of console applications. The constructor is private to
 * prevent instantiation of this utility class.
 */
public class ColorCodes {
    public static final String RESET = "\u001B[0m";
    public static final String HEADER_COLOR = "\033[0;30;100m";
    public static final String TABLE_COLOR_0 = "\u001B[100;48;5;236m";
    public static final String TABLE_COLOR_1 = "\u001B[100;48;5;237m";
    public static final String SEPARATOR_COLOR = "\u001B[48;5;235m";
    public static final String BORDER_STRING = String.format(HEADER_COLOR+" "+RESET);
    public static final String COLUMN_SEPARATOR = String.format(SEPARATOR_COLOR + " " + RESET);

    private ColorCodes(){}

}
