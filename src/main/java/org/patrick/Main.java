package org.patrick;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
    
    static StringBuffer stringBufferOfData = new StringBuffer();
    static String filename = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean fileRead = readFile();
        if (fileRead) {
            writeToFile();
        }
        System.exit(0);
    }
    @SuppressWarnings("finally")
    private static boolean readFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".txt", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getAbsolutePath());
        }
        Scanner fileToRead = null;
        try {
            fileToRead = new Scanner(new File(filename));
            for (String line; fileToRead.hasNextLine() && (line = fileToRead.nextLine()) != null; ) {
                line = line.replaceFirst("2020/[0-9]+/[0-9]+ [0-9]+:[0-9]+:[0-9]+: \\[", "");
                line = line.replaceFirst(",[0-9]*.[0-9]*\\]", "");
                stringBufferOfData.append(line).append("\r\n");
            }
            fileToRead.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("The file " + filename + " could not be found! " + ex.getMessage());
            return false;
        } finally {
            fileToRead.close();
            return true;
        }
    }
    
    private static void writeToFile() {
        try {
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(filename));
            bufwriter.write(stringBufferOfData.toString());
            bufwriter.close();
        } catch (Exception e) {
            System.out.println("Error occured while attempting to write to file: " + e.getMessage());
        }
    }
}
