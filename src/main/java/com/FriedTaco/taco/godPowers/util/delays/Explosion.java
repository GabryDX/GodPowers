package com.FriedTaco.taco.godPowers.util.delays;

import org.bukkit.Location;
import org.bukkit.World;


public class Explosion implements Runnable {
	Location explodeHere;
	int strength;
	boolean fire;

	public Explosion(Location location, int power, boolean setFire) {
		this.explodeHere = location;
		this.fire = setFire;
		this.strength = power;
	}

	public void run() {
		World world = this.explodeHere.getWorld();
		if (world == null)
			return;
		world.createExplosion(this.explodeHere, (float) this.strength, this.fire);
	}
}