package com.example.cxq.gamehuarongdao;

import java.util.ArrayList;
import java.util.Stack;

public class StringToLayout {

    /*
     * conver just like "2, 0, 0S4, 1, 0S2, 3, 0S2, 0, 2S3, 1, 2S2, 3, 2S1, 1, 3S1, 2, 3S1, 0, 4S1, 3, 4" to chesslayout
     */
    public static ArrayList<Chess> convertToChessLayout(String layout) {
        Stack<String> vImages = new Stack<String>();
        vImages.push("guan_v");
        vImages.push("zhang_v");
        vImages.push("zhao_v");
        vImages.push("ma_v");
        vImages.push("huang_v");

        Stack<String> hImages = new Stack<String>();
        hImages.push("huang_h");
        hImages.push("ma_h");
        hImages.push("zhao_h");
        hImages.push("zhang_h");
        hImages.push("guan_h");

        Stack<String> unitImages = new Stack<String>();
        unitImages.push("bing4");
        unitImages.push("bing3");
        unitImages.push("bing2");
        unitImages.push("bing");

        ArrayList<Chess> chessLayout = new ArrayList<Chess>();
        String[] singleChessPiece = layout.split("S");

        for(int i=0; i<singleChessPiece.length; i++) {
            String[] parts = singleChessPiece[i].split(",");
            int posx = Integer.parseInt(parts[1].trim());
            int posy = Integer.parseInt(parts[2].trim());
            int chessType = Integer.parseInt(parts[0].trim());

            if(chessType == 1) {
                chessLayout.add(new Chess(posx, posy, 1, 1, unitImages.pop()));
            } else if(Integer.parseInt(parts[0].trim()) == 2) {
                    chessLayout.add(new Chess(posx, posy, 1, 2, vImages.pop()));
            } else if(Integer.parseInt(parts[0].trim()) == 3) {
                    chessLayout.add(new Chess(posx, posy, 2, 1, hImages.pop()));
            } else if(Integer.parseInt(parts[0].trim()) == 4) {
                chessLayout.add(new Chess(posx, posy, 2, 2, "cao"));
            }
        }
        return chessLayout;
    }
}
