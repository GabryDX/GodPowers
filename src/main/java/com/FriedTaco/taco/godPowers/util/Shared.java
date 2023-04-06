package com.FriedTaco.taco.godPowers.util;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class Shared {
	
	public Shared() {}

	public static List<Entity> getAreaOfEffect(Player dragonBorn, int radius, int length) {
		Location epicenter = dragonBorn.getEyeLocation();
		Vector heading = epicenter.getDirection();
		List<Entity> returnMe = new LinkedList<>();
		length *= 2;

		for (Entity victim : dragonBorn.getNearbyEntities(length, length, length)) {
			Vector dragonBornToVictim = victim.getLocation().subtract(epicenter).toVector();
			double dotProduct = dragonBornToVictim.dot(heading);
			if (!(dotProduct < 0.0)
					&& !(dragonBornToVictim.lengthSquared() - dotProduct * dotProduct > (double) (radius * radius))) {
				returnMe.add(victim);
			}
		}

		return returnMe;
	}
}
