package _appId_.utils.device
{
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isIOS() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "ios" ) >= 0;
	}
}
