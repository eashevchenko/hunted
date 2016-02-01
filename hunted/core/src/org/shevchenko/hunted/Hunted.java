package org.shevchenko.hunted;

import com.badlogic.gdx.Game;
import org.shevchenko.hunted.screen.LoadingScreen;

public class Hunted extends Game {

	@Override
	public void create() {
		setScreen(new LoadingScreen());
	}
	
}
