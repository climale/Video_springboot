package com.video.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FFMPEGconfig {
    private String ffmpegEXE;

    public FFMPEGconfig(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInput,String videoOutput) throws IOException {

        Process process = null;
        try {
        String command = ffmpegEXE +" -i "+videoInput+" -vcodec copy -an "+videoOutput;

            System.out.print(command);


        process = Runtime.getRuntime().exec(command);

            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        InputStream error = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(error);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null){
        }

        if(bufferedReader!=null){
            bufferedReader.close();
        }
        if (inputStreamReader!=null){
            inputStreamReader.close();
        }
        if (error!=null){
            error.close();
        }

    }

    public void voiceandaudio(String videoInput,String mp3,String videoOutPut,double seconds) throws IOException {
        Process process = null;
        try {
            String com= ffmpegEXE +" -i "+videoInput+" -i "+mp3+" -t "+seconds+" -vcodec copy -acodec copy "+videoOutPut;

            System.out.print(com);


            process = Runtime.getRuntime().exec(com);

            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        InputStream error = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(error);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null){
        }

        if(bufferedReader!=null){
            bufferedReader.close();
        }
        if (inputStreamReader!=null){
            inputStreamReader.close();
        }
        if (error!=null){
            error.close();
        }
    }

    public void CatchImage(String videoInput,String ImageOutPut) throws IOException {
        Process process = null;
        try {
            String com= ffmpegEXE +" -ss 00:00:01 -y -i "+videoInput+" -vframes 1 "+ImageOutPut;

            System.out.print(com);


            process = Runtime.getRuntime().exec(com);

            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        InputStream error = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(error);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = bufferedReader.readLine()) != null){
        }

        if(bufferedReader!=null){
            bufferedReader.close();
        }
        if (inputStreamReader!=null){
            inputStreamReader.close();
        }
        if (error!=null){
            error.close();
        }
    }
//    public static void main(String[] args){
//        FFMPEGconfig ffmpeGconfig = new FFMPEGconfig("E:\\ffmpeg\\bin\\ffmpeg.exe");
//        try {
//            ffmpeGconfig.convertor("E:\\video\\new.mp4","E:\\video\\no-audio.mp4");
//            ffmpeGconfig.voiceandaudio("E:\\video\\no-audio.mp4","E:\\video\\mu.mp3","E:\\video\\changeaudio.mp4",10);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
