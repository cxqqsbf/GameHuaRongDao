package com.example.cxq.gamehuarongdao;

public class ChessControl {

    private ChessBoard board;

    public ChessControl(ChessBoard board) {
        this.board = board;
    }

    public boolean addPiece(Chess piece) {
        return this.board.addPiece(piece);
    }
}
