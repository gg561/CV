package me.cv.entities;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;

public enum CustomEntityTypes {

	MERCHANT ("merchant", EntityTypes.VILLAGER, EntityVillager.class, MerchantEntity.class),
	GUARD("guard", EntityTypes.VILLAGER, EntityVillager.class, MerchantEntity.class);
	
    private String name;
    private EntityTypes<? extends EntityInsentient> entityType;
    private Class<? extends EntityInsentient> customClass;
    private Class<? extends EntityInsentient> b;
    
    private CustomEntityTypes(String name, EntityTypes<? extends EntityInsentient> entityType,
            Class<? extends EntityInsentient> customClass,
            Class<? extends EntityInsentient> class1)
    {
        this.name = name;
        this.entityType = entityType;
        this.customClass = customClass;
        this.b = class1;
    }

    CustomEntityTypes(String string, EntityTypes<EntityVillager> villager, Class<MerchantEntity> class1, Object object) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
        return name;
    }

	
	public EntityTypes<?> getEntityType() {
		return entityType;
	}
	
	public Class<? extends EntityInsentient> getCustomClass() {
        return customClass;
    }
	
}
