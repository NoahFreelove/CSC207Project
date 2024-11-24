package com.project.game.tts;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayTTS {

    private static final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;
    private static Thread mainThread;

    static {
        mainThread = Thread.currentThread();
        new Thread(() -> {
            try {
                System.setProperty("freetts.voices",
                        "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
                Synthesizer sy = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
                sy.allocate();
                sy.resume();

                while (running) {
                    if (!mainThread.isAlive()) {
                        running = false;
                        break;
                    }

                    // if queue is empty just continue so we actually check if the main thread is alive
                    if (messageQueue.isEmpty()) {
                        continue;
                    }

                    String message = messageQueue.take();
                    sy.speakPlainText(message, null);
                }

                sy.deallocate();
            } catch (Exception e) {
                System.err.println("Error when trying to play voice <" + e.getClass().getTypeName() + ">: " + e.getMessage());
            }
        }).start();
    }

    public static void say(String message) {
        /*try {
            //messageQueue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error when adding message to queue: " + e.getMessage());
        }

         */
    }

    public static void shutdown() {
        running = false;
    }
}