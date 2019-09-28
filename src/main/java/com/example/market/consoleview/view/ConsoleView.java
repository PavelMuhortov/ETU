package com.example.market.consoleview.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public abstract class ConsoleView {

    public static final int SIZE = 20;

    public static final int NUMBER_SIZE = 5;

    public static String toSize(String text, int size, char toAppend) {
        if (text.length() >= size) {
            if (size <= 3) {
                throw new IllegalArgumentException("Result size must be greater than 3");
            }
            return text.substring(0, size - 3) + "...";
        } else {
            final StringBuilder sb = new StringBuilder(text);
            for (int i = 0; i < size - text.length(); i++) {
                sb.append(toAppend);
            }
            return sb.toString();
        }

    }

    protected void printTable(List<String> columnNames, Map<Long, List<String>> values) {
        System.out.print(toSize("#", NUMBER_SIZE, ' '));
        System.out.print("|");
        for (String propertyName : columnNames) {
            System.out.print(toSize(propertyName, SIZE, ' '));
            System.out.print("|");
        }
        System.out.println();
        for (String propertyName : columnNames) {
            System.out.print(toSize("", SIZE, '_'));
            System.out.print("_");
        }
        System.out.println("______");
        for (Map.Entry<Long, List<String>> entry : values.entrySet()) {
            System.out.print(toSize(String.valueOf(entry.getKey()), NUMBER_SIZE, ' '));
            System.out.print("|");
            for (String property : entry.getValue()) {
                System.out.print(toSize(property, SIZE, ' '));
                System.out.print("|");
            }
            System.out.println();
        }
    }

    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected int askForNumber(int bound) {
        Integer result = -1;
        while (result < 0 || result >= bound) {
            result = askInformation(Scanner::nextInt, result, "Enter number: ");
        }
        return result;
    }

    protected Long askForNumber(Collection<Long> numbers) {
        Long result = -1L;
        while (!numbers.contains(result)) {
            result = askInformation(Scanner::nextLong, result, "Enter number: ");
        }
        return result;
    }

    protected <T> T askInformation(Function<Scanner, T> infoExtractor, T fallback, String invitation) {
        System.out.print(invitation + " ");
        final Scanner scanner = new Scanner(System.in);
        try {
            return infoExtractor.apply(scanner);
        } catch (Exception e) {
            return fallback;
        } finally {
            System.out.print("\033[1A");
            System.out.flush();
        }
    }

    protected String askForAction(List<String> actions) {
        for (int i = 0; i < actions.size(); i++) {
            System.out.print(i + ": " + actions.get(i) + " ");
        }
        System.out.println();
        final int number = askForNumber(actions.size());
        return actions.get(number);
    }

    protected String getValue() {
        return askInformation(Scanner::nextLine, "", "Enter value: ");
    }

    protected boolean askForConfirm() {
        String confirm = "";
        while (!confirm.equals("y") && !confirm.equals("n"))
            confirm = askInformation(Scanner::nextLine, confirm, "Confirm? y/n");
        return confirm.equals("y");
    }
}
