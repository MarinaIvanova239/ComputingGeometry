package com.appmath.geometry;

import com.appmath.custom.MyFileWorker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainTask {

    private static MyFileWorker fileWorker = new MyFileWorker();
    private static GeometryTask task = new GeometryTask();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        if (!in.hasNext()) {
            System.out.println("You should write file's name as an argument!");
            return;
        }

        // Parse arguments
        String inputFileName = in.next();
        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            throw new FileNotFoundException(inputFileName);
        }

        // Worker part
        fileWorker.readDataFile(inputFileName);
        task.setPoints(fileWorker.getPoints());
        task.solveTaskForSquare();
        task.solveTaskForPerimeter();
        fileWorker.writeDataFile("minSquare.txt", task.getAnswerForSquare());
        fileWorker.writeDataFile("minPerimeter.txt", task.getAnswerForPerimeter());
    }
}
