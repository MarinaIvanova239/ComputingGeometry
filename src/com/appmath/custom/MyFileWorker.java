package com.appmath.custom;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;

public class MyFileWorker {

    private ArrayList<Point2D> points = null;

    public ArrayList<Point2D> getPoints() {
        return points;
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
