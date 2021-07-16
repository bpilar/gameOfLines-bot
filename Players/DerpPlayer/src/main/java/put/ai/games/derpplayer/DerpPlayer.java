/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.derpplayer;
//package derpplayer;

import java.util.List;
import java.util.Random;
import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;

public class DerpPlayer extends Player {

    public static void main(String[] args) {}

    private final Random random = new Random(0xbadface);


    @Override
    public String getName() {
        return "someNameHere";
    }


    @Override
    public Move nextMove(Board b) {
        Color derpColor = getColor();
        Color samColor = getOpponent(derpColor);
        List<Move> moves = b.getMovesFor(derpColor);
        Board board = b.clone();
        int size = board.getSize();
        boolean[][] map = new boolean[size][size];
        //int minGroups = size*2;
        Move bestMove = moves.get(random.nextInt(moves.size()));
        long minVar = (long) size *size;
        for (Move move : moves ) {
            board.doMove(move);
            if(board.getWinner(samColor)==derpColor) return move;
            int meanI = 0;
            int meanJ = 0;
            int countFig = 0;
            for (int i=0; i<size; i++) {
                for (int j = 0; j < size; j++) {
                    map[i][j] = false;
                    if (board.getState(j, i)==derpColor) {
                        map[i][j]=true;
                        meanI += i;
                        meanJ += j;
                        countFig++;
                    }
                }
            }
            meanI = meanI / countFig;
            meanJ = meanJ / countFig;
            //int groups = 0;
            long var = 0;
            for (int i=0; i<size; i++) {
                for(int j=0; j<size; j++) {
                    if (map[i][j]) {
                        //check(map, size, i, j);
                        //groups++;
                        var += ((long) (meanI - i) *(meanI-i))+((long) (meanJ - j) *(meanJ-j));
                    }
                }
            }
            var = var / countFig;
            board.undoMove(move);
            //if(groups<minGroups) {
            //    minGroups = groups;
            //    bestMove = move;
            //}
            if (var<minVar) {
                minVar = var;
                bestMove = move;
            }
        }

        return bestMove;
    }

    /*private void check(boolean[][] map, int size, int i, int j) {
        if (map[i][j]) {
            map[i][j]=false;
            if (j>0) {
                check(map, size, i, (j-1));
            }
            if (j<(size-1)) {
                check(map, size, i, (j+1));
            }
            if(i>0) {
                check(map, size, (i-1), j);
                if (j>0) {
                    check(map, size, (i-1), (j-1));
                }
                if (j<(size-1)) {
                    check(map, size, (i-1), (j+1));
                }
            }
            if(i<(size-1)) {
                check(map, size, (i+1), j);
                if (j>0) {
                    check(map, size, (i+1), (j-1));
                }
                if (j<(size-1)) {
                    check(map, size, (i+1), (j+1));
                }
            }
        }
    }*/
}
