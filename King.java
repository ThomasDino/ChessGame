package GameProject;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Color color) {
        super(color, "K");
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDistance = Math.abs(toRow - fromRow);
        int colDistance = Math.abs(toCol - fromCol);
        return (rowDistance <= 1 && colDistance <= 1);
    }

    @Override
    public List<int[]> getValidMoves(int currentRow, int currentCol) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate valid moves for a king
        int[][] possibleMoves = {
            {currentRow - 1, currentCol - 1},
            {currentRow - 1, currentCol},
            {currentRow - 1, currentCol + 1},
            {currentRow, currentCol - 1},
            {currentRow, currentCol + 1},
            {currentRow + 1, currentCol - 1},
            {currentRow + 1, currentCol},
            {currentRow + 1, currentCol + 1}
        };

        for (int[] move : possibleMoves) {
            int row = move[0];
            int col = move[1];
            if (isValidMove(currentRow, currentCol, row, col) && isValidPosition(row, col)) {
                validMoves.add(new int[]{row, col});
            }
        }

        return validMoves;
    }

    @Override
    public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol) {
        return isValidMove(fromRow, fromCol, toRow, toCol);
    }

    // Helper method to check if a position is within the 8x8 board
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}

