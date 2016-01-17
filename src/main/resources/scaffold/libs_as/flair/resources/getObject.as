package flair.resources
{
	/**
	 *
	 */
	public function getObject( id : String , groupID : String = null ) : Object
	{
		return getAssetManager( groupID ).getObject( id );
	}
}
