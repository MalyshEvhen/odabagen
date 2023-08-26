package com.github.malyshevhen.scan;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.github.malyshevhen.GlobalOptions;
import com.github.malyshevhen.util.VideoFileSearchTask;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static picocli.CommandLine.Option;

@Command(name = "scan", description = "Scan files in base folder",
        mixinStandardHelpOptions = true)
public final class ScanCommand implements Runnable {

    public static final String DOUBLE_LINE_SEPARATOR =
            "========================================================";
    public static final String SINGLE_LINE_SEPARATOR =
            "-------------------------------------------------------";
    @Mixin
    GlobalOptions globalOptions;

    @Option(names = {"-i", "--input-folder"},
            description = "Input folder path. Default: current folder")
    File inputFolder = new File(System.getProperty("user.dir"));

    @Option(names = {"-o", "--output-folder"},
            description = "Output folder path. Default: input folder")
    File outputFolder = inputFolder;

    @Override
    public void run() {
        var videoFileSearchTask = new VideoFileSearchTask(inputFolder);
        List<File> videoFiles = videoFileSearchTask.findVideoFiles();

        if (globalOptions.isVerbose()) {
            System.out.println("Scan command run in verbose mode...");

            for (File videoFile : videoFiles) {
                Metadata metadata = null;

                try {
                    System.out.println(DOUBLE_LINE_SEPARATOR);
                    System.out.printf("Get metadata from file: %s%n",
                            videoFile.getName());
                    System.out.println(DOUBLE_LINE_SEPARATOR);

                    metadata = ImageMetadataReader.readMetadata(videoFile);
                } catch (ImageProcessingException | IOException e) {
                    e.printStackTrace();
                }

                /* Extract and print common metadata. */

                if (metadata != null) {
                    printDirectories(metadata);
                }
            }

        } else {
            System.out.println("Scan command run not in verbose mode...");
        }
    }

    private static void printDirectories(Metadata metadata) {

        int counter = 1;

        for (Directory directory : metadata.getDirectories()) {
            System.out.println(SINGLE_LINE_SEPARATOR);
            System.out.printf("Directory: %s%n", directory.getName());
            System.out.println(SINGLE_LINE_SEPARATOR);

            for (Tag tag : directory.getTags()) {
                System.out.println(counter++ + ". "
                        + tag.getTagName() + " | "
                        + tag.getTagType() + " | "
                        + tag.getDescription()
                );
            }
            counter = 0;
        }
    }

}
