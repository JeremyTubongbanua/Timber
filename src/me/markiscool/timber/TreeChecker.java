package me.markiscool.timber;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class TreeChecker {

    private Set<Vector> offsets;
    private Set<XMaterial> validLogs;

    private Set<Block> treeBlocks;

    public TreeChecker() {

        //initializes offsets for branches
        offsets = new HashSet<>();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    offsets.add(new Vector(x, y, z));
                }
            }
        }

        //all of the valid logs
        validLogs = new HashSet<XMaterial>() {{
            add(XMaterial.OAK_LOG);
            add(XMaterial.ACACIA_LOG);
            add(XMaterial.BIRCH_LOG);
            add(XMaterial.DARK_OAK_LOG);
            add(XMaterial.JUNGLE_LOG);
            add(XMaterial.SPRUCE_LOG);
            add(XMaterial.STRIPPED_ACACIA_LOG);
            add(XMaterial.STRIPPED_BIRCH_LOG);
            add(XMaterial.STRIPPED_JUNGLE_LOG);
            add(XMaterial.STRIPPED_OAK_LOG);
        }};

        //initializes sets used in parseTree()
        treeBlocks = new HashSet<>();
    }

    public Set<Block> parseTree(Block startBlock) {

        //add the start block to trunk
        treeBlocks.add(startBlock);

        //searches from start block and upward
        searchTrunk(startBlock);

        //tree must be 3 blocks tall
        if(treeBlocks.size() < 3) {
            return null;
        }

        //for every block within trunk, check for branches
        Set<Block> trunk = new HashSet<Block>(treeBlocks);
        for(Block b : trunk) {
            recursiveBranchSearch(b);
        }

//        Set<Block> branches = new HashSet<Block>(treeBlocks);
//        for(Block b : branches) {
//            recursiveLeafSearch(b;
//        }

        //return tree set
        return treeBlocks;
    }


    private void searchTrunk(Block block) {
        //initial target is the starting block
        Block targetBlock = block;

        //while the block above targetBlock is valid..
        while(isValidType((targetBlock = targetBlock.getRelative(BlockFace.UP)).getType())) {
            //add that target block
            treeBlocks.add(targetBlock);
        }
    }

    private void recursiveBranchSearch(Block block) {
        //checks all offsets
        for(Vector v : offsets) {
            //get the block of that offset
            Block targetBlock = block.getRelative(v.getBlockX(), v.getBlockY(), v.getBlockZ());
            //if the offset is valid,
            if(isValidType((targetBlock.getType())) && !treeBlocks.contains(targetBlock)) {
                treeBlocks.add(targetBlock);
                recursiveBranchSearch(targetBlock);
            }

        }
    }

    //returns true if the material is a valid log
    public boolean isValidType(Material material) {
        return validLogs.contains(XMaterial.fromString(material.name()));
    }

}
