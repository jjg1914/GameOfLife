package com.quesucede.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class GridView extends View {
	
	public static final int PAUSE = 0;
	public static final int RUNNING = 1;
	
	private Life _life;
	
	private RefreshHandler _redrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            GridView.this.update();
            GridView.this.invalidate();
        }

        public void sleep(long delayMillis) {
        	removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };

	public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _life = new Life(context);
        initGridView();
	}
	
	public void setMode(int mode) {
		if (mode == RUNNING) {
			update();
			return;
		}
		if (mode == PAUSE) {
			// TODO: implement.
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.background));

        Paint cell_default = new Paint();
		cell_default.setColor(getResources().getColor(R.color.cell_default));
		Paint cell_low = new Paint();
		cell_low.setColor(getResources().getColor(R.color.cell_low));
		Paint cell_medium = new Paint();
		cell_medium.setColor(getResources().getColor(R.color.cell_medium));
		Paint cell_high = new Paint();
		cell_high.setColor(getResources().getColor(R.color.cell_high));

		// draw background
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		// draw cells
		for (int h = 0; h < Life.HEIGHT; h++) {
			for (int w = 0; w < Life.WIDTH; w++) {
                Paint cell;
                int n = Life.calculateNeighbours( h, w );
                if ( !PreferencesActivity.ENABLE_COLOR_CODING ) {
                    cell = cell_default;
                } else if ( n > 6 ) {
                    cell = cell_high;
                } else if ( n > 3 ) {
                    cell = cell_medium;
                } else {
                    cell = cell_low;
                }
				if (Life.getGrid()[h][w] != 0) {
					canvas.drawRect(
						w * Life.CELL_SIZE, 
						h * Life.CELL_SIZE, 
						(w * Life.CELL_SIZE) + (Life.CELL_SIZE -1),
						(h * Life.CELL_SIZE) + (Life.CELL_SIZE -1),
						cell);
				}
			}
		}
	}
	
	private void update() {
		_life.generateNextGeneration();
		_redrawHandler.sleep(PreferencesActivity.MOVE_DELAY);
	}
	
	private void initNewGame() {
		_life.initializeGrid();
	}
	
	private void initGridView() {
		setFocusable(true);
	}
}
