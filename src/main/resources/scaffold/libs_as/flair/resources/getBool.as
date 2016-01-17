package flair.resources
{
	/**
	 *
	 */
	public function getBool( id : String , groupID : String = null ) : Boolean
	{
		return getAssetManager( groupID ).getXml( "values" ).bool.( @name == id ).toString() == "true";
	}
}
