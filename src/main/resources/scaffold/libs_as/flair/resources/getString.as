package flair.resources
{
	/**
	 *
	 */
	public function getString( id : String , groupID : String = null ) : String
	{
		return getAssetManager( groupID ).getXml( "values" ).string.( @name == id ).toString();
	}
}
