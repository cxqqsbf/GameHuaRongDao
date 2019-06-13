package com.example.cxq.gamehuarongdao;

import java.util.Stack;

public class Chess {
    private Position curr_pos;
    private String id;
    public int width;
    public int height;
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Chess(int posx, int posy, int width, int height, String id) {
        this.width = width;
        this.height = height;
        this.curr_pos = new Position(posx, posy);
        this.id = id;
    }

    public Position getPosition() {
        return this.curr_pos;
    }


    public String getId() {
        return this.id;
    }


    public boolean setLayout() {
        if(this.curr_pos.getPosx() >=0 && this.curr_pos.getPosy() >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public String toString() {
        return String.format("Piece id:%s, posx:%d, posy:%d, width:%d, height:%d", id, curr_pos.getPosx(), curr_pos.getPosy(), width, height);
    }
}
