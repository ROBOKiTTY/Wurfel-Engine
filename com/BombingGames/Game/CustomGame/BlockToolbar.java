package com.BombingGames.Game.CustomGame;

import com.BombingGames.Game.Gameobjects.Block;
import com.BombingGames.Game.Gameobjects.GameObject;
import org.newdawn.slick.Graphics;

/**
 *The Minecraft-like toolbar
 * @author Benedikt Vogler
 */
public class BlockToolbar {
    private int posX;
    private int posY;
    private int[] slot = new int[9];
    private int selection = 0;

    public BlockToolbar() {
        slot[0] = 1;
        slot[1] = 2;
        slot[2] = 3;
        slot[3] = 4;
        slot[4] = 5;
        slot[5] = 6;
        slot[6] = 7;
        slot[7] = 8;
        slot[8] = 10;
    }

    public void setSlot(int slot, int id) {
        this.slot[slot] = id;
    } 
    
     
    public void setPos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
    
    public int getSelectionID(){
        return slot[selection];
    }
    
    public void render(Graphics g){
        GameObject.getSpritesheet().getSprite("toolbar").draw(posX, posY);
        GameObject.getSpritesheet().getSprite("selection").draw(posX+80*selection, posY-5);
        
        for (int i = 0; i < 9; i++) {
            if (slot[i]!=0){
                Block.getBlockSprite(slot[i], 0,1).draw(posX+i*80+17, posY+15, 0.35f);
                Block.getBlockSprite(slot[i], 0,0).draw(posX+i*80+17, posY+15+14, 0.35f);
                Block.getBlockSprite(slot[i], 0,2).draw(posX+i*80+17+28, posY+15+14, 0.35f);
            }
        }
    }
}
