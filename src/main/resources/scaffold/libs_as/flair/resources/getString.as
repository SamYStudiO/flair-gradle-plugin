package flair.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getString( id : String , groupID : String = EnumScreen.MAIN ) : String
	{
		return getAssetManager( groupID ).getXml( "values" ).string.( @name == id ).toString();
	}
}
