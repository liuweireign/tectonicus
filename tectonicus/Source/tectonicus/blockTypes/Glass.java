/*
 * Source code from Tectonicus, http://code.google.com/p/tectonicus/
 *
 * Tectonicus is released under the BSD license (below).
 *
 *
 * Original code John Campbell / "Orangy Tang" / www.triangularpixels.com
 *
 * Copyright (c) 2012, John Campbell
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list
 *     of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice, this
 *     list of conditions and the following disclaimer in the documentation and/or
 *     other materials provided with the distribution.
 *   * Neither the name of 'Tecctonicus' nor the names of
 *     its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package tectonicus.blockTypes;

import tectonicus.BlockContext;
import tectonicus.BlockIds;
import tectonicus.BlockType;
import tectonicus.BlockTypeRegistry;
import tectonicus.rasteriser.Mesh;
import tectonicus.raw.RawChunk;
import tectonicus.renderer.Geometry;
import tectonicus.texture.SubTexture;
import tectonicus.util.Colour4f;

public class Glass implements BlockType
{	
	private final String name;
	private final SubTexture texture;
	
	public Glass(String name, SubTexture texture)
	{
		this.name = name;
		this.texture = texture;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public boolean isSolid()
	{
		return false;
	}
	
	@Override
	public boolean isWater()
	{
		return false;
	}
	
	@Override
	public void addInteriorGeometry(int x, int y, int z, BlockContext world, BlockTypeRegistry registry, RawChunk rawChunk, Geometry geometry)
	{
		addEdgeGeometry(x, y, z, world, registry, rawChunk, geometry);
	}
	
	@Override
	public void addEdgeGeometry(int x, int y, int z, BlockContext world, BlockTypeRegistry registry, RawChunk rawChunk, Geometry geometry)
	{
		Mesh mesh = geometry.getMesh(texture.texture, Geometry.MeshType.Transparent);
		
		Colour4f colour = new Colour4f(1, 1, 1, 1);
		
		final int aboveId = world.getBlockId(rawChunk.getChunkCoord(), x, y+1, z);
		if (aboveId != BlockIds.GLASS)
			BlockUtil.addTop(world, rawChunk, mesh, x, y, z, colour, texture, registry);
		
		final int belowId = world.getBlockId(rawChunk.getChunkCoord(), x, y-1, z);
		if (belowId != BlockIds.GLASS)
			BlockUtil.addBottom(world, rawChunk, mesh, x, y, z, colour, texture, registry);
		
		final int northId = world.getBlockId(rawChunk.getChunkCoord(), x-1, y, z);
		if (northId != BlockIds.GLASS)
			BlockUtil.addNorth(world, rawChunk, mesh, x, y, z, colour, texture, registry);
		
		final int southId = world.getBlockId(rawChunk.getChunkCoord(), x+1, y, z);
		if (southId != BlockIds.GLASS)
			BlockUtil.addSouth(world, rawChunk, mesh, x, y, z, colour, texture, registry);
		
		final int eastId = world.getBlockId(rawChunk.getChunkCoord(), x, y, z-1);
		if (eastId != BlockIds.GLASS)
			BlockUtil.addEast(world, rawChunk, mesh, x, y, z, colour, texture, registry);
		
		final int westId = world.getBlockId(rawChunk.getChunkCoord(), x, y, z+1);
		if (westId != BlockIds.GLASS)
			BlockUtil.addWest(world, rawChunk, mesh, x, y, z, colour, texture, registry);
	}
	
}
