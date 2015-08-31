package capsule.dimension;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class CapsuleWorldProvider extends WorldProvider {

	public CapsuleWorldProvider() {
		this.hasNoSky = true;
	}
	
	@Override
	protected void registerWorldChunkManager()
	{
		int biomeId = this.findAvailableBiomeId();
		super.worldChunkMgr = new WorldChunkManagerHell( new CapsuleBiomeGen(biomeId), 0.0F);
	}

	private int findAvailableBiomeId() {
		BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();
		for( int i = 0; i < biomes.length; i++ ) {
			if( biomes[i] == null ){
				return i;
			}
		}
		return -2;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new CapsuleChunkProvider( this.worldObj, 0 );
	}

	@Override
	public float calculateCelestialAngle( long par1, float par3 )
	{
		return 0;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT )
	public float[] calcSunriseSunsetColors( float celestialAngle, float partialTicks )
	{
        return null;
	}
	
	@Override
	public float getSunBrightnessFactor(float par1) {
		return 1.0F;
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	@SideOnly( Side.CLIENT )
	public boolean isSkyColored()
	{
		return true;
	}

	@Override
	public boolean doesXZShowFog( int par1, int par2 )
	{
		return false;
	}

	@Override
	public boolean isDaytime()
	{
		return true;
	}

	@Override
	public float getStarBrightness( float par1 )
	{
		return 0;
	}

	@Override
	public BlockPos getSpawnPoint()
	{
		return new BlockPos( 0, 0, 0 );
	}

	@Override
	public boolean isBlockHighHumidity(BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean canDoLightning( Chunk chunk )
	{
		return true;
	}
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public String getDimensionName() {
		return "Capsule dimension";
	}

	@Override
	public String getInternalNameSuffix() {
		return null;
	}

}
