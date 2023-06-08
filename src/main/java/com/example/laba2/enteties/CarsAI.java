package com.example.laba2.enteties;

import com.example.laba2.core.CarsRep;
import com.example.laba2.core.Habitat;

import static java.lang.Math.*;

public class CarsAI extends BaseAI {

    @Override
    void move() {
        synchronized (CarsRep.getInstance().getList()) {
            for (CarsInGeneral c : CarsRep.getInstance().getList()) {
                if (!(c instanceof Cars)) continue;
                double x = c.getX();
                double y = c.getY();
                double speed = c.getSpeed();
                double targetX = c.getTargetX();
                double targetY = c.getTargetY();
                if (x == targetX && y == targetY) continue;
                double angle = Math.atan2(targetY - y, targetX - x);
                double dx = (speed * Math.cos(angle));
                double dy = (speed * Math.sin(angle));
                double new_x = x + dx;
                double new_y = y + dy;
                if (distance(x, y, targetX, targetY) <= speed) {
                    c.setX(targetX);
                    c.setY(targetY);
                } else {
                    c.setX(new_x);
                    c.setY(new_y);
                }
            }
        }
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

}
