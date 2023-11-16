package GameProject;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color, "R");
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        return fromRow == toRow || fromCol == toCol;
    }

    @Override
    public List<int[]> getValidMoves(int currentRow, int currentCol) {
        List<int[]> validMoves = new ArrayList<>();

        // Generate valid moves for a rook
        for (int row = 0; row < 8; row++) {
            if (row != currentRow) {
                validMoves.add(new int[]{row, currentCol});
            }
        }

        for (int col = 0; col < 8; col++) {
            if (col != currentCol) {
                validMoves.add(new int[]{currentRow, col});
            }
        }

        return validMoves;
    }

    @Override
    public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol) {
        return isValidMove(fromRow, fromCol, toRow, toCol);
    }
    
    
}

