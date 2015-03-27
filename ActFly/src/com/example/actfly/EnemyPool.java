package com.example.actfly;

import org.andengine.util.adt.pool.GenericPool;

public class EnemyPool extends GenericPool {
	 
    public static EnemyPool instance;
 
    public static EnemyPool sharedEnemyPool() {
        if (instance == null)
            instance = new EnemyPool();
        return instance;
    }
 
    private EnemyPool() {
        super();
    }
 
    @Override
    protected Enemy onAllocatePoolItem() {
        return new Enemy();
    }
 
    /** Called when a projectile is sent to the pool */
    protected void onHandleRecycleItem(final Enemy e) {
        e.spriteF.setVisible(false);
        e.spriteF.detachSelf();
        e.clean();
    }
    
    protected void onHandleObtainItem(Enemy pItem) {
        pItem.init();
    }
    
    
}