package _appId_.model
{
	import _appId_.view.EnumScreen;

	import starling.textures.Texture;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getTexture( assetID : String, groupID : String = EnumScreen.MAIN ) : Texture
	{
		return getAssetManager( groupID ).getTexture( assetID );
	}
}
