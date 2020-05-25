package Controller;

import model.Grid;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContollerTest {

    @Test
    public void assertWin() {
        assertTrue(Contoller.areFourConnected(new int[][] {
                {1,1,1,1,2,2,2},
                {2,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        },1));
        assertFalse(Contoller.areFourConnected(new int[][] {
                {1,1,0,1,2,2,2},
                {2,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        },1));
    }

    @Test
    public void assertDiskAddition() {
        Grid grid = new Grid();
        grid.setBoard(new int[][] {
                {1,1,0,1,2,2,2},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        });
        grid.setPlayerTurn(1);
        Grid grid2 = new Grid();
        grid2.setBoard(new int[][] {
                {1,1,1,1,2,2,2},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}
        });
        grid2.setPlayerTurn(2);
        assertFalse(grid.getBoard() == grid2.getBoard());
        assertFalse(Contoller.addDisc(grid,1) == grid2);

    }
    @Test
    public void assertDraw() {
        Grid grid = new Grid();
        grid.setBoard(new int[][] {
                {1,1,1,2,2,2,1},
                {2,2,2,1,1,1,2},
                {1,1,1,2,2,2,1},
                {2,2,2,1,1,1,2},
                {1,1,1,2,2,2,1},
                {2,2,2,1,1,1,2}
        });
        assertTrue(Contoller.isFull(grid));
    }
}
