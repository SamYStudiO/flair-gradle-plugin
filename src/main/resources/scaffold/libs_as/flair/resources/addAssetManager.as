package flair.resources
{
	import starling.utils.AssetManager;

	/**
	 *
	 */
	public function addAssetManager( manager : AssetManager , id : String ) : void
	{
		assetManagers[ id ] = manager;
	}
}
