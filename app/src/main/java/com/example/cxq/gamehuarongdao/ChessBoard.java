package com.example.cxq.gamehuarongdao;

import java.util.ArrayList;

public class ChessBoard {
    ArrayList<Chess> onlinePieces = new ArrayList<Chess>();
    public int width;
    public int height;

    //棋盘的布局，二维数组的方式显示，1代表有棋子，0代表没有棋子
    public int boardArray[][] = new int[this.height][this.width];
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ChessBoard(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /*
     * 增加棋盘上的棋子
     */
    public boolean  addPiece(Chess piece) {
        onlinePieces.add(piece);
        if(!setLayout()) {
            removePiece(piece.getId());
            return false;
        }
        return true;
    }

    /*
     * 去除位置不对的棋子
     */
    public boolean removePiece(String id) {
        for(int i=0; i<onlinePieces.size(); i++) {
            if(onlinePieces.get(i).getId() == id) {
                onlinePieces.remove(i);
                setLayout();
                return true;
            }
        }
        return false;
    }

    /*
     * 返回所有棋盘上的棋子
     */
    public ArrayList<Chess> getAllPieces() {
        return this.onlinePieces;
    }


    /*
     * 对棋盘重新布局
     * 如果有不正确的布局，返回false
     */
    public boolean setLayout() {
        //check if all pieces layout valid

        int[][] prevBoardArray = DeepCopy.deepCopyInt(this.boardArray);
        this.boardArray = new int[this.height][this.width];

        for(int i=0; i<onlinePieces.size(); i++) {
            Chess piece = onlinePieces.get(i);

            if((piece.getPosition().getPosx() + piece.getWidth()) > width ||
                    (piece.getPosition().getPosy()+ piece.getHeight()) > height ||
                    piece.getPosition().getPosx() < 0 ||
                    piece.getPosition().getPosy() < 0) {
                return false;
            }

            for(int posy=piece.getPosition().getPosy(); posy<piece.getPosition().getPosy()+piece.getHeight(); posy++) {
                for(int posx=piece.getPosition().getPosx(); posx<piece.getPosition().getPosx()+piece.getWidth(); posx++) {
                    if(this.boardArray[posy][posx] != 0) {
                        boardArray = prevBoardArray;
                        return false;
                    }
                    this.boardArray[posy][posx] = 1;
                }
            }

        }
        return true;
    }
public void retrieve(int x,int y,Chess piece)
{
    piece.getPosition().posx=x;
    piece.getPosition().posy=y;
}

    public int[][] getLayoutArray() {
        return DeepCopy.deepCopyInt(this.boardArray);
    }


    public Chess getPiece(String id) {
        for(int i=0; i<onlinePieces.size(); i++) {
            if(onlinePieces.get(i).getId() == id) {
                System.out.println("hello");
                return onlinePieces.get(i);
            }
        }
        return null;
    }
}
