package _appId_.resources
{
	import _appId_.view.EnumScreen;

	import starling.textures.Texture;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDrawables( prefix : String , groupID : String = EnumScreen.MAIN ) : Vector.<Texture>
	{
		return getAssetManager( groupID ).getTextures( prefix );
	}
}
