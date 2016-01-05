package flair.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getColor( id : String , groupID : String = EnumScreen.MAIN ) : uint
	{
		return uint( "0x" + getAssetManager( groupID ).getXml( "values" ).color.( @name == id ).toString().replace( "#" , "" ) );
	}
}
