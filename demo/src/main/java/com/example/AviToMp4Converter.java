package com.example;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;

import java.io.File;
import java.io.FilenameFilter;

public class AviToMp4Converter {

    public static void main(String[] args) {
        // Habilitar registros de FFmpeg
        avutil.av_log_set_level(avutil.AV_LOG_INFO);

        String inputFolder = "C:\\Users\\Brahyan\\Desktop\\testeoJava\\test-recordings";
        String outputFolder = "C:\\Users\\Brahyan\\Desktop\\testeoJava\\test-recordings";

        File folder = new File(inputFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("La carpeta especificada no existe o no es una carpeta.");
            return;
        }

        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        FilenameFilter aviFilter = (dir, name) -> name.toLowerCase().endsWith(".avi");

        File[] aviFiles = folder.listFiles(aviFilter);

        if (aviFiles == null || aviFiles.length == 0) {
            System.out.println("No se encontraron archivos AVI en la carpeta especificada.");
            return;
        }

        for (File aviFile : aviFiles) {
            String outputFilePath = outputFolder + File.separator + aviFile.getName().replace(".avi", ".mp4");
            try {
                convertAviToMp4(aviFile.getAbsolutePath(), outputFilePath);
                System.out.println("Convertido: " + aviFile.getName());
            } catch (Exception e) {
                System.err.println("Error al convertir el archivo: " + aviFile.getName());
                e.printStackTrace();
            }
        }

        System.out.println("Conversión completada.");
    }

    public static void convertAviToMp4(String inputFile, String outputFile) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, grabber.getImageWidth(), grabber.getImageHeight());
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // Utilizando el codec H.264 para MP4
        recorder.setFormat("mp4");
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);

        recorder.start();

        Frame frame;
        while ((frame = grabber.grabFrame()) != null) {
            recorder.record(frame);
        }

        grabber.stop();
        recorder.stop();

        grabber.release();
        recorder.release();
    }
}
