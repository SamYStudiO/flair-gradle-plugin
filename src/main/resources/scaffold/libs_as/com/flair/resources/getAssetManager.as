package com.flair.resources
{
	import _appId_.view.EnumScreen;

	import starling.utils.AssetManager;

	/**
	 *
	 */
	public function getAssetManager( id : String = EnumScreen.MAIN ) : AssetManager
	{
		return assetManagers[ id ];
	}
}
