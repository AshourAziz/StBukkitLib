package com.stealthyone.mcb.stbukkitlib.updates;

public class UpdateCheckRunnable implements Runnable {

    private UpdateChecker updateChecker;

    public UpdateCheckRunnable(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }

    @Override
    public void run() {
        updateChecker.checkForUpdates(true);
    }

}