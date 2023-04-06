package com.FriedTaco.taco.godPowers.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.FriedTaco.taco.godPowers.util.delays.Explosion;
import com.FriedTaco.taco.godPowers.util.delays.RemoveEntity;

public class SuperpowersUtils {

	private final double[] fusHoriStrength = new double[] { 0.5, 2.0, 7.0 };
	private final double[] fusVertStrength = new double[] { 0.5, 0.7, 1.5 };
	private final EntityType[] heroes = new EntityType[] { EntityType.SNOWMAN, EntityType.WOLF, EntityType.IRON_GOLEM };
	private final Set<Biome> coldBiomes = Set.of(Biome.FROZEN_OCEAN, Biome.FROZEN_RIVER);
	private final Set<Biome> forestBiomes = Set.of(Biome.FOREST, Biome.BIRCH_FOREST);

	private static SuperpowersUtils instance;

	public static SuperpowersUtils getInstance() {
		if (instance == null) {
			instance = new SuperpowersUtils();
		}
		return instance;
	}

	public void encage(Player player, Material material) {
		World world = player.getWorld();
		int level = 3;
		int distance = 5 * level;
		if (material == null) {
			material = Material.GLASS;
		}

		List<Vector> cubes = new ArrayList<>();
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				for (int w = -2; w <= 2; w++) {
					if ((Math.abs(i) == 2 || Math.abs(j) == 2 || Math.abs(w) == 2)
							&& !(Math.abs(i) == 2 && Math.abs(j) == 2)
							&& !(Math.abs(i) == 2 && Math.abs(w) == 2)
							&& !(Math.abs(j) == 2 && Math.abs(w) == 2)) {
						cubes.add(new Vector(i, j+1, w));
					}
				}
			}
		}

		for (Entity victim : Shared.getAreaOfEffect(player, 4, distance)) {
			Location location = victim.getLocation();
			for (int i = 0; i < cubes.size(); i++) {
				Vector cube = cubes.get(i);
				Block block = world.getBlockAt((int) (location.getX() + cube.getX()),
						(int) (location.getY() + cube.getY()), (int) (location.getZ() + cube.getZ()));
				if (block.getType() != Material.BEDROCK && block.getType() != Material.OBSIDIAN) {
					block.setType(material, false); // Backwards compat with 1.7 >.> LOST
				}
			}
		}

	}

	public void fusRoDah(Player player, Plugin plugin) {
		World world = player.getWorld();
		Location location = player.getLocation();

		int level = 3;
		int distance = 5 * level;
		Vector heading = player.getEyeLocation().getDirection();
		Vector blastVector = new Vector();
		blastVector.copy(heading).setY(0).normalize();
		blastVector.multiply(fusHoriStrength[level - 1]).setY(fusVertStrength[level - 1]);

		for (Entity victim : Shared.getAreaOfEffect(player, 4, distance)) {
			victim.setVelocity(victim.getVelocity().add(blastVector));
		}

		world.playEffect(location, Effect.GHAST_SHOOT, 0, distance + 10);

		Set<Material> nullSet = null;
		if (level >= 2) {
			List<Block> sight = player.getLineOfSight(nullSet, 4);
			world.createExplosion(sight.get(sight.size() - 1).getLocation(), 0.0F);
		}

		if (level == 3) {
			List<Block> sight = player.getLineOfSight(nullSet, 32);

			for (int i = 8; i < 32 && i < sight.size(); i += 6) {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
						new Explosion(sight.get(i).getLocation(), 0, false), i / 3);
			}
		}
	}

	public void kamikaze(Player player) {
		World world = player.getWorld();
		Location location = player.getLocation();
		world.createExplosion(location, 40F);
		world.createExplosion(location, 20F);
		world.createExplosion(location, 10F);
		world.createExplosion(location, 5F);
	}

	public void spawnHero(Player player, Plugin plugin) {
		Biome currentBiome = player.getLocation().getBlock().getBiome();
		Set<Material> nullSet = null;
		Location spawnHere = player.getLastTwoTargetBlocks(nullSet, 30).get(0).getLocation();
		spawnHere.add(0, 1, 0);
		World world = spawnHere.getWorld();
		if (world == null)
			return;

		LivingEntity hero = null;
		if (this.coldBiomes.contains(currentBiome)) {
			hero = (LivingEntity) world.spawnEntity(spawnHere, this.heroes[0]);
		} else if (this.forestBiomes.contains(currentBiome)) {
			hero = (LivingEntity) world.spawnEntity(spawnHere, this.heroes[1]);
		} else {
			hero = (LivingEntity) world.spawnEntity(spawnHere, this.heroes[2]);
		}

		if (hero instanceof Tameable) {
			((Tameable) hero).setOwner(player);
		} else if (hero instanceof IronGolem) {
			((IronGolem) hero).setPlayerCreated(true);
		}

		hero.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 20));
		world.createExplosion(spawnHere, 0.0F, false);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RemoveEntity(hero, true), 1200L);
	}

	public void vulcan(Player player) {
		World world = player.getWorld();
		Location location = player.getLocation();
		Fireball f = world.spawn(location.add(location.getDirection().normalize().multiply(3).toLocation(world,
				location.getYaw(), location.getPitch())).add(0, 1D, 0), Fireball.class); // Wow this long
		f.setShooter(player);
	}

	public void zeus(Player player) {
		World world = player.getWorld();
		world.strikeLightning((player.getTargetBlock((Set<Material>) null, 100).getLocation())); // p.getTargetBlock is
																									// a Magic Value!
	}

	public void zeusEvil(Player player) {
		World world = player.getWorld();

		Block block = player.getTargetBlock((Set<Material>) null, 100);
		// Location blockLoc = block.getLocation();
		// w.strikeLightning(blockLoc);
		ArrayList<Block> airBlocks = new ArrayList<Block>();
		ArrayList<Block> realBlocks = new ArrayList<Block>();
		Block blockAir;
		for (int x = -2; x < 3; x++) {
			for (int y = -1; y < 50; y++) {
				for (int z = -2; z < 3; z++) {
					blockAir = world.getBlockAt((int) (block.getX() + x), (int) (block.getY() + y),
							(int) (block.getZ() + z));
					if (blockAir.isEmpty()) // if (ba.getType() == Material.AIR)
						airBlocks.add(blockAir);
					else
						realBlocks.add(blockAir);
				}
			}
		}
		player.setFireTicks(0);
		for (Block ba : realBlocks) {
			world.strikeLightning(ba.getLocation());
		}
		for (Block ba : airBlocks) {
			ba.setType(Material.FIRE);
		}
//    	w.strikeLightning(blockLoc);
//    	Block blockAir = event.getPlayer().getWorld().getBlockAt((int) blockLoc.getX(), (int) blockLoc.getY(), (int) (blockLoc.getZ() + 1));
//        if (blockAir.getType() == Material.AIR) {
//            event.getPlayer().setFireTicks(0);
//            blockAir.setType(Material.FIRE);
//        }
	}

}
