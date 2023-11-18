package GameProject;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color, "B");
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece piece) {
        int rowDistance = Math.abs(toRow - fromRow);
        int colDistance = Math.abs(toCol - fromCol);
        return rowDistance == colDistance;
    }

    @Override
    public List<int[]> getValidMoves(int currentRow, int currentCol, Piece piece) {
        List<int[]> validMoves = new ArrayList();

        // Generate valid moves for a bishop
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (isValidMove(currentRow, currentCol, row, col, piece)) {
                    validMoves.add(new int[]{row, col});
                }
            }
        }

        return validMoves;
    }

    @Override
    public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol) {
        return isValidMove(fromRow, fromCol, toRow, toCol, null);
    }
}

