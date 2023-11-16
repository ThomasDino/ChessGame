package GameProject;
import java.util.Scanner;

import GameProject.Piece.Color;

public class ChessGame {
	private ChessBoard chessboard;
	private Color currentPlayer;

	public ChessGame(ChessBoard chessboard) {
		this.chessboard = chessboard;
		this.currentPlayer = Color.WHITE; // White starts the game
	}

	// Other methods for game logic, player moves, and game flow
	//

	// ...
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Color currentPlayer, ChessBoard chessboard) {
		Piece activePiece = chessboard.getPiece(fromRow, fromCol);
		System.out.println("Color of piece is: " + activePiece.getColor());
		
		System.out.println("Piece is " + activePiece); 
		if (activePiece != null && activePiece.getColor() == currentPlayer) {
			System.out.println("Check isValidMove1");
			if (activePiece.isValidMove(fromRow, fromCol, toRow, toCol, chessboard)) {
				System.out.println("Check isValidMove2");
				// Check if the path is clear, castling rules, en passant, etc.
				if (isPathClear(fromRow, fromCol, toRow, toCol)) {
					return true;
				}
			}
		}

		return false;
	}
	private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
		// Check if the path from source to target is clear for piece movement
		// Implement path clearance logic based on the specific piece's movement rules
		// For example, check if there are no pieces in the way for rooks, bishops, etc.
		// This implementation depends on the piece class and their movement rules.
		return true; // Placeholder
	}

	public void promotePawn(int row, int col, String newPieceType) {
		Piece promotedPiece = createPromotedPiece(newPieceType);
		chessboard.setPiece(row, col, promotedPiece);
	}

	public Piece createPromotedPiece(String pieceType) {
		switch (pieceType) {
		case "Queen":
			return new Queen(currentPlayer);
		case "Rook":
			return new Rook(currentPlayer);
		case "Knight":
			return new Knight(currentPlayer);
		case "Bishop":
			return new Bishop(currentPlayer);
		default:
			// Handle the default case or error condition as needed
			return null;
		}
	}


	public boolean isCheck() {
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

	public boolean canKingEscapeCheck(int kingRow, int kingCol) {
		// Iterate through all neighboring squares around the king
		for (int row = kingRow - 1; row <= kingRow + 1; row++) {
			for (int col = kingCol - 1; col <= kingCol + 1; col++) {
				if (row >= 0 && row < 8 && col >= 0 && col < 8) {
					if (isValidMove(kingRow, kingCol, row, col, currentPlayer)) {
						// The king can move to this square, so it's not in check
						return true;
					}
				}
			}
		}
		return false; // The king has no legal moves to escape check
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean  isCheckmate() {
		Color currentPlayer = getCurrentPlayer();
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

	public boolean canPieceBlockCheck(int kingRow, int kingCol) {
		// Find the threatening piece
		Piece threateningPiece = null;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = chessboard.getPiece(row, col);
				if (piece != null && piece.getColor() != currentPlayer && piece.isAttacking(row, col, kingRow, kingCol)) {
					threateningPiece = piece;
					break;
				}
			}
		}

		if (threateningPiece != null) {
			// Check if any of the player's pieces can block the attack on the king
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					Piece piece = chessboard.getPiece(row, col);
					if (piece != null && piece.getColor() == currentPlayer) {
						if (piece.isValidMove(row, col, kingRow, kingCol)) {
							if (isPathClear(row, col, kingRow, kingCol)) {
								// A player's piece can block the check
								return true;
							}
						}
					}
				}
			}
		}

		return false; // No piece can block the check
	}

	public void printBoard() {
		System.out.println("   .a      .b       .c       .d       .e       .f       .g       .h");
		for (int row = 7; row >= 0; row--) { // Start from row 7 (top) and go down to 0 (bottom)
			for (int col = 0; col < 8; col++) {
				Piece piece = chessboard.getPiece(row, col);
				if (piece != null) {
					if (piece.getColor() == Color.WHITE) {
						System.out.print(" " + "[" + "W" + piece.getName() + "]" + "    ");
					} else if (piece.getColor() == Color.BLACK) {
						System.out.print(" " + "[" + "B" + piece.getName() + "]" + "    ");
					}

				} else {
					System.out.print(" [  ]    ");
				}
			}
			System.out.println(" " + (row + 1)); // Reverse the numbering
		}
		System.out.println("   .a      .b       .c       .d       .e       .f       .g       .h");
	} 


	public void movePiece(int fromRow, int fromCol, int toRow, int toCol, Color currentPlayer) {
		Piece piece = chessboard.getPiece(fromRow, fromCol);

		if (piece == null || piece.getColor() != currentPlayer) {

			// Check if the piece belongs to the current player and is not null
			System.out.println("Check movePiece1");
			System.out.println("Invalid move. Try again.");
		}



		if (!piece.isValidMove(fromRow, fromCol, toRow, toCol)) {
			// Check if the move is valid for the piece
			System.out.println("Check movePiece2");
			System.out.println("Invalid move. Try again.");
		}

		if (!chessboard.isValidMove(fromRow, fromCol, toRow, toCol, chessboard, currentPlayer)) {

			// Check if the move is legal (e.g., not putting the king in check)
			System.out.println("Check movePiece3");
			System.out.println("Invalid move. Try again.");
		}

		// Perform the move
		chessboard.movePiece(fromRow, fromCol, toRow, toCol);
		if (chessboard.isCheck(chessboard, currentPlayer)) {
			System.out.println("Your king is in check!");
			if (chessboard.isCheckmate(chessboard, currentPlayer)) {
				System.out.println("Checkmate! " + currentPlayer + " lost the game.");
				// You can add a game-ending logic here
			}
		}

		// Switch to the next player's turn
	}

	public void switchPlayer() {
		if (currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;
		} 
		else {
			currentPlayer = Color.WHITE;
		}
		System.out.println("Switched to player: " + currentPlayer);

	}



	public static void main(String[] args) {
		ChessBoard chessboard = new ChessBoard();
		ChessGame chessGame = new ChessGame(chessboard);
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Chess!");
		// Color currentPlayer = Color.WHITE;

		while (true) {
			// Display the current board
			chessGame.printBoard();


			// Get user input for the move
			System.out.print("Enter your move (e.g., 'e2 e4'): ");
			String moveInput = scanner.nextLine();

			if (moveInput.length() != 5) {
				System.out.println("Invalid input. Please enter a valid move.a ");
				continue;
			}

			String[] moveParts = moveInput.split(" ");
			if (moveParts.length != 2) {
				System.out.println("Invalid input. Please enter a valid move.b");
				continue;
			}

			String fromSquare = moveParts[0];
			String toSquare = moveParts[1];

			char fromFile = fromSquare.charAt(0);
			char fromRank = fromSquare.charAt(1);
			char toFile = toSquare.charAt(0);
			char toRank = toSquare.charAt(1);

			int fromCol = 0;
			int toCol = 0;

			switch(fromFile) {
			case 'a': 
				fromCol = 0;
				break;
			case 'b': 
				fromCol = 1;
				break;
			case 'c': 
				fromCol = 2;
				break;
			case 'd': 
				fromCol = 3;
				break;
			case 'e': 
				fromCol = 4;
				break;
			case 'f': 
				fromCol = 5;
				break;
			case 'g': 
				fromCol = 6;
				break;
			case 'h': 
				fromCol = 7;
				break;
			}

			switch(toFile) {
			case 'a': 
				toCol = 0;
				break;
			case 'b': 
				toCol = 1;
				break;
			case 'c': 
				toCol = 2;
				break;
			case 'd': 
				toCol = 3;
				break;
			case 'e': 
				toCol = 4;
				break;
			case 'f': 
				toCol = 5;
				break;
			case 'g': 
				toCol = 6;
				break;
			case 'h': 
				toCol = 7;
				break;
			}




			int fromRow = Integer.parseInt(String .valueOf(fromRank)) - 1;
			int toRow = Integer.parseInt(String .valueOf(toRank)) - 1;

			System.out.println(fromCol);
			System.out.println(fromRow);

			System.out.println(toCol);
			System.out.println(toRow);


			// Check if the move is valid and make the move
			if (chessGame.isValidMove(fromRow, fromCol, toRow, toCol, chessGame.getCurrentPlayer())) {
				chessGame.movePiece(fromRow,  fromCol, toRow, toCol, chessGame.getCurrentPlayer());

				// Check for check and checkmate conditions
				if (chessGame.isCheck()) {
					System.out.println("Your king is in check!");
					if (chessGame.isCheckmate()) {
						System.out.println("Checkmate! You lost the game.");
						break;
					}
				}

				// Switch to the next player's turn
				chessGame.switchPlayer();
				
			} else {

				System.out.println("check");
				System.out.println("Invalid move. Try again.");
			}
		}

		scanner.close();
	}
}


