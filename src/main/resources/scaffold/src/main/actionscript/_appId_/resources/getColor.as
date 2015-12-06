package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getColor( id : String , groupID : String = EnumScreen.MAIN ) : uint
	{
		return uint( "0x" + getAssetManager( groupID ).getXml( "values" )..color.( @name == id ).toString().replace( "#" , "" ) );
	}
}
