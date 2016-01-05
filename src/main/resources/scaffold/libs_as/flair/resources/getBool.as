package flair.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getBool( id : String , groupID : String = EnumScreen.MAIN ) : Boolean
	{
		return getAssetManager( groupID ).getXml( "values" ).bool.( @name == id ).toString() == "true";
	}
}
