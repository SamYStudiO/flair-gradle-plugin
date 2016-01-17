package flair.resources
{
	/**
	 *
	 */
	public function getColor( id : String , groupID : String = null ) : uint
	{
		return uint( "0x" + getAssetManager( groupID ).getXml( "values" ).color.( @name == id ).toString().replace( "#" , "" ) );
	}
}
