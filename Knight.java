package GameProject;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color, "N");
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece piece) {
        int rowDistance = Math.abs(toRow - fromRow);
        int colDistance = Math.abs(toCol - fromCol);
        return (rowDistance == 2 && colDistance == 1) || (rowDistance == 1 && colDistance == 2);
    }

    @Override
    public List<int[]> getValidMoves(int currentRow, int currentCol, Piece piece) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate valid moves for a knight
        int[][] possibleMoves = {
            {currentRow - 2, currentCol - 1},
            {currentRow - 2, currentCol + 1},
            {currentRow - 1, currentCol - 2},
            {currentRow - 1, currentCol + 2},
            {currentRow + 1, currentCol - 2},
            {currentRow + 1, currentCol + 2},
            {currentRow + 2, currentCol - 1},
            {currentRow + 2, currentCol + 1}
        };

        for (int[] move : possibleMoves) {
            int row = move[0];
            int col = move[1];
            if (piece.isValidMove(currentRow, currentCol, row, col, piece) && isValidPosition(row, col)) {
                validMoves.add(new int[]{row, col});
            }
        }

        return validMoves;
    }

    @Override
    public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol, Piece piece) {
        return piece.isValidMove(fromRow, fromCol, toRow, toCol, piece);
    }

    // Helper method to check if a position is within the 8x8 board
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
