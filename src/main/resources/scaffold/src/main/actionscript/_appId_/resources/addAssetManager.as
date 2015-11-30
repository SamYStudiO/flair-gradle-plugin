package _appId_.resources
{
	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO (contact@samystudio.net) on 29/11/2015.
	 */
	public function addAssetManager( manager : AssetManager , id : String ) : void
	{
		assetManagers[ id ] = manager;
	}
}
