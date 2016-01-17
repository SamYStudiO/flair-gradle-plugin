package flair.resources
{
	import starling.utils.AssetManager;

	/**
	 *
	 */
	public function getAssetManager( id : String = null ) : AssetManager
	{
		return assetManagers[ id ];
	}
}
