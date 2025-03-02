import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileReaderByType {
    private String path;
    private boolean addToExistingFiles;
    private int statistics = 0;

    private FileWriter stringWriter = null;
    private FileWriter floatWriter = null;
    private FileWriter integerWriter = null;

    private StatisticsHandler integerStatisticsHandler = null;
    private StatisticsHandler floatStatisticsHandler = null;
    private StatisticsHandler stringStatisticsHandler = null;

    public FileReaderByType(String path, boolean addToExistingFiles, int statistics) {
        this.path = path;
        this.addToExistingFiles = addToExistingFiles;
        this.statistics = statistics;
    }

    public void readFiles(List<String> inputFiles) throws IOException {
        for (String file : inputFiles) {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                try {
                    Long value = Long.parseLong(line);
                    integerWriter = writeValue("integers.txt", integerWriter, line);

                    if (statistics > 0) {
                        if (integerStatisticsHandler == null) {
                            integerStatisticsHandler = new StatisticsHandler(statistics > 1);
                        }
                        integerStatisticsHandler.fillStatistics(value);
                    }
                    ;
                    continue;
                } catch (IllegalArgumentException e) {
                }

                try {
                    Double value = Double.parseDouble(line);
                    floatWriter = writeValue("floats.txt", floatWriter, line);

                    if (statistics > 0) {
                        if (floatStatisticsHandler == null) {
                            floatStatisticsHandler = new StatisticsHandler(statistics > 1);
                        }
                        floatStatisticsHandler.fillStatistics(value);
                    }
                    ;
                    continue;
                } catch (IllegalArgumentException e) {
                }

                stringWriter = writeValue("strings.txt", stringWriter, line);

                if (statistics > 0) {
                    if (stringStatisticsHandler == null) {
                        stringStatisticsHandler = new StatisticsHandler(statistics > 1);
                    }
                    stringStatisticsHandler.fillStatistics(line.length());
                }
            }
            scanner.close();
        }

        if (statistics > 0) {
            printStatistics(integerStatisticsHandler, statistics > 1, "Integers:", false);
            printStatistics(floatStatisticsHandler, statistics > 1, "Floats:", true);
            printStatistics(stringStatisticsHandler, statistics > 1, "Strings:", false);
        }

        closeWriter(integerWriter);
        closeWriter(floatWriter);
        closeWriter(stringWriter);
    }

    private FileWriter getFile(String typeName)
            throws IOException, IllegalArgumentException {
        return new FileWriter(path + typeName, addToExistingFiles);
    }

    private FileWriter writeValue(String fileName, FileWriter fileWriter, String line) throws IOException {
        if (fileWriter == null) {
            fileWriter = getFile(fileName);
        }
        fileWriter.write(line + "\r\n");
        return fileWriter;
    }

    private void printStatistics(StatisticsHandler statisticsHandler, boolean fullStatistics, String title, boolean needDouble) {
        System.out.println("\r\n" + title + "\r\nNumber: " + statisticsHandler.getQuantity());
        if (fullStatistics) {
            if (needDouble) {
                System.out.println("Max value: " + statisticsHandler.getMax(needDouble));
                System.out.println("Min value: " + statisticsHandler.getMin(needDouble));
            } else {
                System.out.println("Max value: " + statisticsHandler.getMax());
                System.out.println("Min value: " + statisticsHandler.getMin());
            }
        }
    }

    private void closeWriter(FileWriter fileWriter) throws IOException {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
}
