package pl.grzegorzekkk.carpathsearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileStatsLogger {
    private int step;
    private PrintWriter pw;
    private FileWriter fw;

    public FileStatsLogger(String fileName) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        step = 0;

        sb.append(fileName)
                .append('-')
                .append(dateFormat.format(date))
                .append(".txt");

        File file = new File(sb.toString());

        try {
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            pw.println("# time, crossing, roads left");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(int crossing, int distanceLeft) {
        pw.println(step + " " + crossing + " " + distanceLeft);
        step++;
    }

    public void close() {
        try {
            fw.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
