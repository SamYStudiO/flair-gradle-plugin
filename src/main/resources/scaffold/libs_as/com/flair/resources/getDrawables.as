package com.flair.resources
{
	import _appId_.view.EnumScreen;

	import starling.textures.Texture;

	/**
	 *
	 */
	public function getDrawables( prefix : String , groupID : String = EnumScreen.MAIN ) : Vector.<Texture>
	{
		return getAssetManager( groupID ).getTextures( prefix );
	}
}
