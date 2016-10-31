package com.appmath.custom;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class MyFileWorker {

    private ArrayList<MyPoint2D> points = null;

    public ArrayList<MyPoint2D> getPoints() {
        return points;
    }

    private void convertTextToPoints(String inputText) {
        char[] lineArray  = inputText.toCharArray();
        int i = 0;
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ZERO;
        while (lineArray[i] != 0) {
            if (lineArray[i] == ',') {
                x = y;
                y = BigInteger.ZERO;
            }
            else if (lineArray[i] == '\n') {
                points.add(new MyPoint2D(x, y));
                x = BigInteger.ZERO;
                y = BigInteger.ZERO;
            }
            else if (lineArray[i] >= '0' && lineArray[i] <= '9') {
                int number = lineArray[i] - '0';
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
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
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

    public void writeDataFile(String outputFileName, String answer) {
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
