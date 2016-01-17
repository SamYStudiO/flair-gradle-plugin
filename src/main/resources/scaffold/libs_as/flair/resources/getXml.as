package flair.resources
{
	/**
	 *
	 */
	public function getXml( id : String , groupID : String = null ) : XML
	{
		return getAssetManager( groupID ).getXml( id );
	}
}
