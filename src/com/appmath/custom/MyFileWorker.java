package com.appmath.custom;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class MyFileWorker {

    private ArrayList<MyPoint2D> points = new ArrayList<MyPoint2D>();

    public ArrayList<MyPoint2D> getPoints() {
        return points;
    }

    private void convertTextToPoints(String inputText) {
        int i = 0;
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ZERO;
        int textLength = inputText.length();
        while (i < textLength) {
            char currentSymbol = inputText.charAt(i);
            if (currentSymbol == ',') {
                x = y;
                y = BigInteger.ZERO;
            }
            else if (currentSymbol == '\n') {
                points.add(new MyPoint2D(x, y));
                x = BigInteger.ZERO;
                y = BigInteger.ZERO;
            }
            else if (currentSymbol  >= '0' && currentSymbol <= '9') {
                int number = currentSymbol - '0';
                y = y.multiply(BigInteger.TEN);
                y = y.add(BigInteger.valueOf(number));
            }
            i++;
        }
    }

    public void readDataFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        String resultText = sb.toString();
        convertTextToPoints(resultText);
    }

    private String convertPointsToText(int[] points) {
        String text = new String();
        for (int i = 0; i < points.length; i++) {
            text += String.valueOf(points[i]) + ' ';
        }

        return text;
    }

    public void writeDataFile(String outputFileName, int[] answerPoints) {
        String answer = convertPointsToText(answerPoints);
        File file = new File(outputFileName);
        try {
            if(!file.exists()){
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print(answer);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
