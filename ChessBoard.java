package GameProject;

import GameProject.Piece.Color;

public class ChessBoard {
	private static Piece[][] board;

	public ChessBoard() {
		board = new Piece[8][8];
		initializeBoard();
	}

	// Initialize the chessboard with pieces in their starting positions
	private void initializeBoard() {
		// Initialize Pawns
		for (int col = 0; col < 8; col++) {
			board[1][col] = new Pawn(Color.WHITE);
			board[6][col] = new Pawn(Color.BLACK);
		}

		// Initialize Rooks
		board[0][0] = new Rook(Color.WHITE);
		board[0][7] = new Rook(Color.WHITE);
		board[7][0] = new Rook(Color.BLACK);
		board[7][7] = new Rook(Color.BLACK);

		// Initialize Knights
		board[0][1] = new Knight(Color.WHITE);
		board[0][6] = new Knight(Color.WHITE);
		board[7][1] = new Knight(Color.BLACK);
		board[7][6] = new Knight(Color.BLACK);

		// Initialize Bishops
		board[0][2] = new Bishop(Color.WHITE);
		board[0][5] = new Bishop(Color.WHITE);
		board[7][2] = new Bishop(Color.BLACK);
		board[7][5] = new Bishop(Color.BLACK);

		// Initialize Queens
		board[0][3] = new Queen(Color.WHITE);
		board[7][3] = new Queen(Color.BLACK);

		// Initialize Kings
		board[0][4] = new King(Color.WHITE);
		board[7][4] = new King(Color.BLACK);
	}

	public static Piece getPiece(int row, int col) {
		return board[row][col];
	}

	public void setPiece(int row, int col, Piece piece) {
		board[row][col] = piece;
	}

	public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
		Piece piece = getPiece(fromRow, fromCol);
		setPiece(toRow, toCol, piece);
		setPiece(fromRow, fromCol, null);
	}
	

	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece activePiece, Color currentPlayer) {

		if (activePiece != null && activePiece.getColor() == currentPlayer) {
			if (activePiece.isValidMove(fromRow, fromCol, toRow, toCol, activePiece)) {
				// Check if the path is clear, castling rules, en passant, etc.
				if (isPathClear(fromRow, fromCol, toRow, toCol)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
	    int rowDiff = toRow - fromRow;
	    int colDiff = toCol - fromCol;

	    int rowDirection = Integer.compare(rowDiff, 0);
	    int colDirection = Integer.compare(colDiff, 0);

	    int currentRow = fromRow + rowDirection;
	    int currentCol = fromCol + colDirection;

	    while (currentRow != toRow || currentCol != toCol) {
	        if (board[currentRow][currentCol] != null) {
	            return false; // Path is not clear
	        }

	        currentRow += rowDirection;
	        currentCol += colDirection;
	    }

	    return true; // Path is clear
	}
	
	public boolean isCheck(ChessBoard chessboard, Color currentPlayer) {
		int kingRow = -1, kingCol = -1;
		Color opposingPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

		// Find the current player's king
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = chessboard.getPiece(row, col);
				if (piece instanceof King && piece.getColor() == currentPlayer) {
					kingRow = row;
					kingCol = col;
					break;
				}
			}
		}

		// Check if the opposing player's pieces can attack the king's position
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = chessboard.getPiece(row, col);
				if (piece != null && piece.getColor() == opposingPlayer) {
					if (piece.isAttacking(row, col, kingRow, kingCol)) {
						return true; // King is in check
					}
				}
			}
		}

		return false; // King is not in check
	}
	
	public boolean isCheckmate(ChessBoard chessboard, Color currentPlayer) {
	    int kingRow = -1;
	    int kingCol = -1;

	    // Find the current player's king
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Piece piece = chessboard.getPiece(row, col);
	            if (piece instanceof King && piece.getColor() == currentPlayer) {
	                kingRow = row;
	                kingCol = col;
	                break;
	            }
	        }
	        if (kingRow != -1) {
	            break;
	        }
	    }

	    if (kingRow == -1 || kingCol == -1) {
	        // King not found, something is wrong with the game state
	        return false;
	    }

	    // Check if the king can escape check or a piece can block the check
	    if (chessboard.canKingEscapeCheck(kingRow, kingCol, chessboard, currentPlayer) || chessboard.canPieceBlockCheck(currentPlayer, kingRow, kingCol)) {
	        return false; // King can escape check or a piece can block the check
	    }

	    return true; // No legal moves to escape or block the check; it's checkmate
	}

	
	public boolean canKingEscapeCheck(int kingRow, int kingCol, ChessBoard chessboard, Color currentPlayer) {
		// Iterate through all neighboring squares around the king
		for (int row = kingRow - 1; row <= kingRow + 1; row++) {
			for (int col = kingCol - 1; col <= kingCol + 1; col++) {
				if (row >= 0 && row < 8 && col >= 0 && col < 8) {
					if (isValidMove(kingRow, kingCol, row, col, getPiece(kingCol, kingCol), currentPlayer)) {
						// The king can move to this square, so it's not in check
						return true;
					}
				}
			}
		}
		return false; // The king has no legal moves to escape check
	}


	public boolean canPieceBlockCheck(Color currentPlayer, int kingRow, int kingCol) {
	    // Find the current player's king
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Piece piece = getPiece(row, col);
	            if (piece instanceof King && piece.getColor() == currentPlayer) {
	                kingRow = row;
	                kingCol = col;
	                break;
	            }
	        }
	    }

	    // Initialize variables to store threatening piece coordinates
	    int threateningPieceRow = -1;
	    int threateningPieceCol = -1;

	    // Find the threatening piece
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Piece piece = getPiece(row, col);
	            if (piece != null && piece.getColor() != currentPlayer && piece.isAttacking(row, col, kingRow, kingCol)) {
	                // We found a threatening piece
	                threateningPieceRow = row;
	                threateningPieceCol = col;
	                break;
	            }
	        }
	    }

	    if (threateningPieceRow == -1 || threateningPieceCol == -1) {
	        // No threatening piece was found, something is wrong with the game state
	        return false;
	    }

	    // Check if any of the player's pieces can block the attack on the king
	    for (int row = 0; row < 8; row++) {
	        for (int col = 0; col < 8; col++) {
	            Piece piece = getPiece(row, col);
	            if (piece != null && piece.getColor() == currentPlayer) {
	                if (piece.isValidMove(row, col, kingRow, kingCol, piece)) {
	                    if (isPathClear(row, col, kingRow, kingCol)) {
	                        // A player's piece can block the check
	                        return true;
	                    }
	                }
	            }
	        }
	    }

	    return false; // No piece can block the check
	}



}
