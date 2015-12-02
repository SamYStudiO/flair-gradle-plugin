package _appId_.utils.device
{
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isAndroid() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "android" ) >= 0;
	}
}
