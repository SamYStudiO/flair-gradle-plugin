package _appId_.model
{
	import _appId_.actors.ASSET_MANAGER;
	import _appId_.view.EnumScreen;

	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getAssetManager( id : String = EnumScreen.MAIN ) : AssetManager
	{
		return ASSET_MANAGER[ id ];
	}
}
