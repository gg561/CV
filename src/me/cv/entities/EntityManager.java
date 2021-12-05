package me.cv.entities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.util.ReflectionUtil;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import net.minecraft.server.v1_16_R3.DataConverterRegistry;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.RegistryMaterials;
import net.minecraft.server.v1_16_R3.SharedConstants;
import net.minecraft.server.v1_16_R3.World;

public class EntityManager {
	
	private static HashMap<CustomEntityTypes, Entity> entities = new HashMap<CustomEntityTypes, Entity>();
	private static HashMap<UUID, Entity> guards = new HashMap<UUID, Entity>();
	
	public void spawnEntity(CustomEntityTypes type, String[] args) {
		Entity entity = getEntity(type);
		
	}
	
	private Entity getEntity(CustomEntityTypes type) {
		return entities.get(type);
	}
	
	public void saveNBT(NBTTagCompound nbt, Entity entity) {
		entity.save(nbt);
	}
	
	public static void save(Entity entity) {
		guards.put(entity.getUniqueID(), entity);
	}
	
	public static void remove(Entity entity) {
		guards.remove(entity.getUniqueID());
	}
	
	public static HashMap<UUID, Entity> getGuards(){
		return guards;
	}
	
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
        try {
     
            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }
     
            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }
     
            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

    public <T extends Object> NBTTagCompound getTag(T object){
        NBTTagCompound compound = new NBTTagCompound();
        
        Class<? extends Object> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods){
            if ((method.getName() == "load") && (method.getParameterTypes().length == 1) && (method.getParameterTypes()[0] == NBTTagCompound.class)){
                try {
                    method.invoke(object, compound);
                } catch (Exception e) {
                	e.getCause();
                    e.printStackTrace();
                }
            }
        }
        return compound;
    }
    
    @SuppressWarnings("unchecked")
    public static void register(String name, int id, Class<?> registryClass) {
       ((Map)getPrivateField("c", EntityTypes.class, null)).put(name, registryClass);
       ((Map)getPrivateField("bo", EntityTypes.class, null)).put(registryClass, name);
       ((Map)getPrivateField("f", EntityTypes.class, null)).put(registryClass, id);
    }
    
    @Nullable
    public static Object getPrivateField(String fieldName, @Nonnull Class<?> clazz, Object object) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void registerEntityTypes() {
    	//register("guard", 108, Guards.class);
    	//register("merchant", 109, Guards.class);
    }

}
