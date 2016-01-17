package flair.resources
{
	/**
	 *
	 */
	public function removeAssetManager( id : String = null ) : void
	{
		delete assetManagers[ id ];
	}
}
