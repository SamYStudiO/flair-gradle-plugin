package flair.utils.device
{
	import flash.system.Capabilities;

	/**
	 *
	 */
	public function isWindows() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "windows" ) >= 0;
	}
}
