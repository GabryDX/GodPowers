package com.FriedTaco.taco.godPowers.util.delays;

import org.bukkit.entity.Entity;

public class RemoveEntity implements Runnable {
	Entity removeMe;
	boolean explode;

	public RemoveEntity(Entity victim) {
		this.removeMe = victim;
		this.explode = false;
	}

	public RemoveEntity(Entity victim, boolean explode) {
		this.removeMe = victim;
		this.explode = explode;
	}

	public void run() {
		if (this.removeMe != null) {
			if (this.explode) {
				this.removeMe.getWorld().createExplosion(this.removeMe.getLocation(), 0.0F);
			}

			if (!this.removeMe.isDead()) {
				this.removeMe.remove();
			}
		}
	}
}
