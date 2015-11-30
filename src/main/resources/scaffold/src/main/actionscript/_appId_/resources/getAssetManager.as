package _appId_.resources
{
	import _appId_.view.EnumScreen;

	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getAssetManager( id : String = EnumScreen.MAIN ) : AssetManager
	{
		return assetManagers[ id ];
	}
}
