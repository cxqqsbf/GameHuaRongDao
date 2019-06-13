package com.example.cxq.gamehuarongdao;

import android.annotation.SuppressLint;

import java.util.Arrays;

public class DeepCopy {
    @SuppressLint("NewApi")
    public static int[][] deepCopyInt(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
}
