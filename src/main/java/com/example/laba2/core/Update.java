package com.example.laba2.core;

import com.example.laba2.Interface.ICarsController;
import javafx.application.Platform;

import java.util.TimerTask;

public class Update extends TimerTask
{
    private final ICarsController controller;
    private boolean firstRun = true;
    private long startTime;
    private long lastTime = 0;
    private final int delta;

    public Update(ICarsController controller, int delta)
    {
        this.controller = controller;
        this.delta = delta + 1;
    }


    @Override
    public void run()
    {
        if (firstRun)
        {
            startTime = System.currentTimeMillis();
            lastTime = startTime;
            firstRun = false;
        }
        long currentTime = System.currentTimeMillis();
        double elapsed = (currentTime - startTime) / 1000.0 + delta;
        double frameTime = (lastTime - startTime) / 1000.0;
        Platform.runLater(() -> controller.update(elapsed, frameTime));
        lastTime = currentTime;

    }
}
