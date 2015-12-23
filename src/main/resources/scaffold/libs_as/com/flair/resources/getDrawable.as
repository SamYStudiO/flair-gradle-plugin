package com.flair.resources
{
	import _appId_.view.EnumScreen;

	import starling.textures.Texture;

	/**
	 *
	 */
	public function getDrawable( assetID : String , groupID : String = EnumScreen.MAIN ) : Texture
	{
		return getAssetManager( groupID ).getTexture( assetID );
	}
}
