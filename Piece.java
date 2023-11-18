package GameProject;

import java.util.List;

public class Piece {
    private Color color;
    private String name;

    public Piece(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    // You can add more methods and logic specific to chess pieces here
    public enum Color {
        WHITE,
        BLACK
    }
    
 // Common method to check if a move is valid for a piece
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece activePiece) {
        // Default implementation in the base class (can be overridden in specific piece classes)
        return true;
    }

    // Common method to get valid moves for a piece
    public List<int[]> getValidMoves(int fromRow, int fromCol, Piece piece) {
    	
    	
        // Default implementation in the base class (can be overridden in specific piece classes)
        return null;
    }

    // Common method to check if a piece is attacking a specific square
    public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol) {
        // Default implementation in the base class (can be overridden in specific piece classes)
        return false;
    }

	public String getSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol, Piece activePiece) {
		// TODO Auto-generated method stub
		return false;
	}


}
