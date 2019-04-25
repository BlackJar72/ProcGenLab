package jaredbgreat.procgenlab.generators;

import jaredbgreat.procgenlab.api.IGenerator;
import jaredbgreat.procgenlab.api.IPalette;

/**
 * This is another class for the infinite noise system, re-write, a kind of do 
 * over you might say -- one that hopes to be more data-orient (as far as Java 
 * will allow that and it will work with OOP games).  The hope is to move away 
 * from strict OOP toward the use of transformations on tables (arrays) of 
 * bytes / ints for many operations, with looser coupling, looser encapsulation, 
 * greater flexibility / re-usability (in a functional kind of way), and more 
 * careful consideration of how operations are likely to actually occur in the 
 * hardware.
 * 
 * It will also (hopefully) be geared more toward taking inputs and producing 
 * outputs that allow it to be more readily inserted directly into a game. That 
 * game for now will likely be Minecraft (or, really, me Climatic Biomes mod), 
 * though with the likelihood it could also work in an original game with 
 * similar considerations (procedural development of an "infinite" world in 
 * small chunks, and in a Climatic Biomes style hierarchy of scales and 
 * regions).
 * 
 * 
 * @author Jared Blackburn
 */
public class DOInfNoise implements IGenerator {

    @Override
    public void generate(Long seed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[][] getData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getParameters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParameters(String param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IPalette[] getColorPaletes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getLayerNames() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumLayers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
