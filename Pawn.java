package GameProject;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
	public Pawn(Color color) {
		super(color, "P");
	}


	//@Override
	//    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
	//    	
	//        int forwardDirection = (getColor() == Color.WHITE) ? -1 : 1;
	//        int rowDiff = toRow - fromRow;
	//        int colDiff = Math.abs(toCol - fromCol);
	//        
	//        
	//        if (rowDiff == forwardDirection && colDiff == 0) {
	//          
	//            return true;
	//        }
	//
	//        if (rowDiff == 2 * forwardDirection && colDiff == 0 && (fromRow == 1 || fromRow == 6)) {
	//            
	//            return true;
	//        }
	//
	//        if (rowDiff == forwardDirection && colDiff == 1) {
	//           
	//            return true;
	//        }
	//
	//        return false;
	//    }

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece activePiece, ChessBoard chessboard) {
		if (getColor() == Color.WHITE && toRow - fromRow <= 2 && fromCol == toCol && fromRow == 1) {			
			return true;
		}
		else if (getColor() == Color.WHITE && toRow - fromRow == 1 && fromCol == toCol){
			return true;
		} 
		else if (getColor() == Color.WHITE && fromRow < toRow && Math.abs(toCol - fromCol) == 1 && activePiece.isAttacking(fromRow, fromCol, toRow, toCol)){
			return true;
		} 
		else if (getColor() == Color.BLACK && fromRow - toRow == 2 && fromCol == toCol && fromRow == 6) {
			return true;
		}
		else if (getColor() == Color.BLACK && fromRow - toRow == 1 && fromCol == toCol) {
			return true;
		}
		else if (getColor() == Color.BLACK && fromRow > toRow && Math.abs(toCol - fromCol) == 1 && activePiece.isAttacking(fromRow, fromCol, toRow, toCol)){
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public List<int[]> getValidMoves(int fromRow, int fromCol) {
		List<int[]> validMoves = new ArrayList<>();
		int forwardDirection = (getColor() == Color.WHITE) ? -1 : 1;

		if (isValidMove(fromRow, fromCol, fromRow + forwardDirection, fromCol)) {
			validMoves.add(new int[] {fromRow + forwardDirection, fromCol});

			if ((fromRow == 1 && getColor() == Color.WHITE) || (fromRow == 6 && getColor() == Color.BLACK)) {
				if (isValidMove(fromRow, fromCol, fromRow + 2 * forwardDirection, fromCol)) {
					validMoves.add(new int[] {fromRow + 2 * forwardDirection, fromCol});
				}
			}
		}

		// Pawn captures
		if (isValidMove(fromRow, fromCol, fromRow + forwardDirection, fromCol - 1)) {
			validMoves.add(new int[] {fromRow + forwardDirection, fromCol - 1});
		}

		if (isValidMove(fromRow, fromCol, fromRow + forwardDirection, fromCol + 1)) {
			validMoves.add(new int[] {fromRow + forwardDirection, fromCol + 1});
		}

		return validMoves;
	}

	@Override
	public boolean isAttacking(int fromRow, int fromCol, int toRow, int toCol) {
	    if () {
	    	
	    }
	}

//	    // Check if there is a piece at the target position
//	    if (pieceAtPosition != null) {
//	        // Check if the attacking piece is a white pawn and the target piece is black
//	        if (activePiece.getColor() == Piece.Color.WHITE && pieceAtPosition.getColor() == Piece.Color.BLACK) {
//	            // Check if the move is diagonal
//	            if (Math.abs(toCol - fromCol) == 1 && (toRow - fromRow) == 1) {
//	                return true;
//	            }
//	        }
//	        // Check if the attacking piece is a black pawn and the target piece is white
//	        else if (activePiece.getColor() == Piece.Color.BLACK && pieceAtPosition.getColor() == Piece.Color.WHITE) {
//	            // Check if the move is diagonal
//	            if (Math.abs(toCol - fromCol) == 1 && (fromRow - toRow) == 1) {
//	                return true;
//	            }
//	        }
//	    }
//
//	    // If none of the conditions are met, return false
//	    return false;
//	}

		
		
//		int forwardDirection = (getColor() == Color.WHITE) ? -1 : 1;
//		int rowDiff = toRow - fromRow;
//		int colDiff = Math.abs(toCol - fromCol);
//
//		// A pawn attacks by moving one square diagonally forward
//		return (rowDiff == forwardDirection && colDiff == 1);
//	}

	public boolean canPromote(int toRow) {
		return (getColor() == Color.WHITE && toRow == 0) || (getColor() == Color.BLACK && toRow == 7);
	}
}
