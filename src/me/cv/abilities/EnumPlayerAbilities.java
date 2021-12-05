package me.cv.abilities;

public enum EnumPlayerAbilities {
	
	FIRE("abi_Fire", "fire", EnumActivateType.CROUCH, new Fire()),
	GROUND_HEAT("abi_ground_heat", "ground_heat", EnumActivateType.RIGHT_CLICK, new GroundHeat()), //Tiny's ability
	PIG_PSYCHIC("abi_pig_control", "pig_control", EnumActivateType.CONSTANT, new PigPsychic()), //Bacon's ability - control pigs
	INCREASED_HEALING("abi_inc_healing", "inc_healing", EnumActivateType.DAMAGE, new IncHealing()), //Orange's ability - fast healing
	DOUBLE_PERSONA("abi_double_persona", "double_persona", EnumActivateType.CUSTOM, new DoublePersona()), //Irish's ability - double personality
	SWORDSMENSHIP("abi_swordsmenship", "swordsmenship", EnumActivateType.DAMAGE, new Swordsmen()), //Ethur's ability - better at swords
	ELECTRICITY("abi_electricity", "electricity", EnumActivateType.RIGHT_CLICK, new Electricity()), //Looney's ability - control lightning
	RESSURECTION("abi_ressurection", "ressurection", EnumActivateType.CUSTOM, new Ressurection()), //Kyle's ability - revive the dead
	GUST("abi_gust", "gust", EnumActivateType.RIGHT_CLICK, new Gust()), //Jelly's ability - control wind
	NIGHT_FURY("abi_night_fury", "night_fury", EnumActivateType.ENVIRONMENT, new NightFury()), //Night's ability - invisible during day, faster during night
	HALF_OF_BOTH("abi_half_of_both", "half_of_both", EnumActivateType.CUSTOM, new HalfOfBoth()), //Theseus' ability - Teleport + silk touch hands
	MUD("abi_mud", "mud", EnumActivateType.CUSTOM, new Mud()), //Even's ability - turn to mud
	JESUS("abi_jesus", "jesus", EnumActivateType.ENVIRONMENT, new Jesus()), //Nai's ability - fast + walk on water
	TRICK("abi_trick", "trick", EnumActivateType.EAT, new Trick()); //Fossil's ability - invisible particles
	
	private String tag;
	private String path;
	private EnumActivateType activate;
	private Ability ability;
	
	private EnumPlayerAbilities(String tag, String path, EnumActivateType activate, Ability ability) {
		this.tag = tag;
		this.activate = activate;
		this.path = path;
		this.ability = ability;
	}
	
	public String getTag() {
		return tag;
	}
	
	public EnumActivateType getActivate() {
		return activate;
	}
	
	public Ability getAbility(){
		return ability;
	}
	
	public String getPath() {
		return path;
	}

}
