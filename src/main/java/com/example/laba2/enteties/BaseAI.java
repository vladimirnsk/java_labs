package com.example.laba2.enteties;

abstract class BaseAI extends Thread {
    boolean active = false;
    abstract void move();
    public synchronized void startAI(){
        active = true;
        notify();
    }
    public void stopAI(){
        active = false;
    }

    @Override
    public void run() {
        while(true){
            if(!active){
                try {
                    synchronized (this){
                        if (!active) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            move();
            try {
                Thread.sleep(1000/120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean isActive(){return active;}
    public void setAIPriority(int priority) {
        setPriority(priority);
    }
}
