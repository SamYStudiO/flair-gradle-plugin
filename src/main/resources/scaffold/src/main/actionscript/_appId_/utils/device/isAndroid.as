package _appId_.utils.device
{
	import flash.system.Capabilities;

	/**
	 *
	 */
	public function isAndroid() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "android" ) >= 0;
	}
}
