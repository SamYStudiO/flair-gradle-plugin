package flair.resources
{
	/**
	 *
	 */
	public function getInteger( id : String , groupID : String = null ) : int
	{
		return int( getAssetManager( groupID ).getXml( "values" ).integer.( @name == id ) );
	}
}
