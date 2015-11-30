package _appId_.resources
{
	import _appId_.view.EnumScreen;

	import starling.textures.Texture;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getTexture( assetID : String , groupID : String = EnumScreen.MAIN ) : Texture
	{
		return getAssetManager( groupID ).getTexture( assetID );
	}
}
