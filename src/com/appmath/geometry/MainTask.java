package com.appmath.geometry;

import com.appmath.custom.MyFileWorker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainTask {

    private static MyFileWorker fileWorker = new MyFileWorker();
    private static GeometryTask task = new GeometryTask();

    public static void main(String[] args) throws FileNotFoundException {
        // Parse arguments
        String inputFileName = args[0];
        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            throw new FileNotFoundException(inputFileName);
        }

        // Worker part
        fileWorker.readDataFile(inputFileName);
        task.setPoints(fileWorker.getPoints());
        task.solveTask();
        fileWorker.writeDataFile("minSquare.txt", task.getAnswerForSquare());
        fileWorker.writeDataFile("minPerimeter.txt", task.getAnswerForPerimeter());
    }
}
