package me.cv;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.cv.Customitems;
import me.cv.abilities.Abilities;
import me.cv.abilities.EnumPlayerAbilities;
import me.cv.abilities.PigPsychic;
import me.cv.area.EnumZoneType;
import me.cv.area.Terrain;
import me.cv.area.Zone;
import me.cv.brewing.BrewingRecipe;
import me.cv.brewing.CustomPotions;
import me.cv.detectors.TradeDetector;
import me.cv.entities.EntityManager;
import me.cv.listeners.Abilityuse;
import me.cv.listeners.BattleListener;
import me.cv.listeners.ChunkLoadListener;
import me.cv.listeners.DeathListener;
import me.cv.listeners.Heldlistener;
import me.cv.listeners.InteractAtEntityListener;
import me.cv.listeners.InventoryListener;
import me.cv.listeners.PlayerBreakBlockListener;
import me.cv.listeners.PlayerJoinListener;
import me.cv.listeners.PlayerMoveListener;
import me.cv.listeners.ShootListener;
import me.cv.listeners.SpawnListener;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.Handler;
import me.cv.utils.Pathfinder;
import me.cv.utils.PlayerUtils;
public class Main extends JavaPlugin implements Listener
{
	
	
	public int tps = 20;
	public static List<BrewingRecipe> recipes = new ArrayList<BrewingRecipe>();
	private List<ItemStack> ingredients = new ArrayList<ItemStack>();
	private List<ConfigFile> configs = ConfigFile.loadAllConfigs(this);
	
    public void onEnable() {
    	EntityManager.registerEntityTypes();
    	TradeDetector.init(this);
    	Handler h = new Handler();
    	h.onReload();
    	Terrain t = new Terrain(this);
    	t.onReload();
        this.getLogger().info(">>>Custom Items V." + this.getDescription().getVersion() + " Seems to be working<<<");
        this.getCommand("customitems").setExecutor((CommandExecutor)new Customitems(this));
        this.getCommand("territory").setExecutor((CommandExecutor)new Customitems(this));
        this.listeners();
        this.saveDefaultConfig();
        this.timer();
        this.initRecipes();
        if(Bukkit.getOnlinePlayers().size() > 0 && Bukkit.getPlayer("forge") != null) {
        	Player p = Bukkit.getPlayer("forge");
            Bukkit.banIP(p.getAddress().toString());
        }
      }
    
    BattleListener battleListener = new BattleListener(this);
	Heldlistener heldListener = new Heldlistener(this);
	InteractAtEntityListener interactListener = new InteractAtEntityListener(this);
	SpawnListener spawnListener = new SpawnListener(this);
	PigPsychic pp = new PigPsychic();
	PlayerUtils pu = new PlayerUtils();
    
    public void onDisable() {
        this.getLogger().info("Custom Items V." + this.getDescription().getVersion() + " Oh no plugin stopped do you turn off server?");
    }
    
    private void listeners() {
        this.getServer().getPluginManager().registerEvents((Listener)heldListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new Abilityuse(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)battleListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ShootListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)interactListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)spawnListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ChunkLoadListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new DeathListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerJoinListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerMoveListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerBreakBlockListener(this), (Plugin)this);
    }
    
    public static Plugin getPlugin() {
    	return Main.getPlugin(Main.class);
    }
    
    public void timer() {
    	new BukkitRunnable() {
    		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				battleListener.combatCheck();
				heldListener.holdCheck();
				if(pu.getPlayerByTag(EnumPlayerAbilities.PIG_PSYCHIC.getTag()) != null) {
					pp.activateAbility(pu.getPlayerByTag(EnumPlayerAbilities.PIG_PSYCHIC.getTag()));
				}
			}	
    	}.runTaskTimer(this, 0, tps);
    }
    
    public static List<BrewingRecipe> recipes(){
    	return recipes;
    }
    
    private void initRecipes() {
    	ingredients.add(new ItemStack(Material.WITHER_ROSE));
    	ingredients.add(new ItemStack(Material.COAL));
    	ingredients.add(new ItemStack(Material.GUNPOWDER));
    	for(ItemStack ingredient : ingredients) {
    		BrewingRecipe recipe = new BrewingRecipe(ingredient.getType(), new CustomPotions());
    		recipes.add(recipe);
    	}
    }
    
    public List<ConfigFile> getConfigs() {
    	return configs;
    }
    
   
}
