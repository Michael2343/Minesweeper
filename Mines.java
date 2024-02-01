package mines;

import java.util.Random;

public class Mines {
    Random rand = new Random();
    public static class Place{
    	private boolean bomb;
    	private boolean open;
    	private boolean flag;
    	private int nearBombs;
    	
		public Place() {
			this.bomb = false;
			this.open = false;
			this.flag = false;
			this.nearBombs = 0;
		}
		public int getNearBombs() {
			return nearBombs;
		}
		
		public boolean getFlag() {
			return flag;
		}
		
		public boolean getBomb() {
			return bomb;
		}
		
		public void SetBomb() {
			bomb = true;
		}
		
		public void addBombCount() {
			nearBombs++;
		}
		
	
		public boolean getOpen() {
			return open;
		}
		
		public void setOpen() {
			open=true;
		}
		
		public void resetOpen() {
			open=false;
		}
		
		public void setFlag() {
			flag = !flag;
		}
		
		@Override
		public String toString() {
			if (nearBombs==0) return " ";
			else return String.format("%d", getNearBombs());
			}

	}
	
	private Place[][] board;
	private int height,width;
	private boolean showAll;
	
	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		board = new Place[height][width];
		for(int i = 0; i<height;i++) {
			for(int j = 0; j<width;j++) {
				board[i][j] = new Place();
			}
		}
		while(numMines!=0) {
			if(addMine(rand.nextInt(height), rand.nextInt(width))) numMines--;	
		}
		showAll=false;
		
	}
	
	public boolean addMine(int i, int j) {
		if(board[i][j].getBomb()) return false;
		else {
			board[i][j].SetBomb();
			addNear(i,j);
		}
		return true;
	}
	
	private void addNear(int i, int j) {
		for(int x=i-1;x<=i+1;x++) {
			for(int y=j-1;y<=j+1;y++) {
				if (x==i&& y==j) {continue;}
				else if(x>=0&&x<height && y>=0&&y<width ) {
					board[x][y].addBombCount();					
				}
			}
		}
	}

	public boolean open(int i, int j) {
		if(board[i][j].getBomb()) return false;
		if(!board[i][j].getOpen()){
			board[i][j].setOpen();
			if(board[i][j].getNearBombs()==0) {
				for(int x=i-1;x<=i+1;x++) {
					for(int y=j-1;y<=j+1;y++) {
						if(x>=0&&x<height && y>=0&&y<width) 
							open(x, y);	
					}
				}
			}
		}	
		return true;

	}
	
	public void toggleFlag(int i, int j) {
		board[i][j].setFlag();
	}
	
	public boolean isDone() {
		for(int i = 0; i<height;i++) {
			for(int j = 0; j<width;j++) {
				if(!board[i][j].getBomb() && !board[i][j].getOpen()) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	public String get(int i, int j) {
		
		if(board[i][j].getOpen() || showAll) {
			if (board[i][j].getBomb()) return "X";
			else {
				return board[i][j].toString();
			}
		}
		else {
			if (board[i][j].getFlag()) return "F";
			else return ".";	
		}
	}
	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
	

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		for(int i = 0; i<height;i++) {
			for(int j = 0; j<width;j++) {
				str.append(get(i,j));
			}
			str.append("\n");
		}
		return str.toString();
	}

}
